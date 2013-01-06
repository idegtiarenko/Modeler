package com.gman.modeler.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.gman.modeler.api.exceptions.ModelingException;

/**
 * Perform modeling itself. Stateful
 *
 * @author gman
 */
public class Modeler implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Queue> income = new ArrayList<>();
    private final List<Service> services = new ArrayList<>();
    private final List<RequestType> types = new ArrayList<>();
    private final List<StatisticAggregator> statistics = new ArrayList<>();
    private final List<Request> generated = new ArrayList<>();
    private final ProcessedQueue outcome = new ProcessedQueue();
    private final AbortedQueue aborted = new AbortedQueue();
    private double statisticsInterval = DEFAULT_STATISTICS_INTERVAL;

    public Modeler() {
    }

    public List<Queue> getIncome() {
        return income;
    }

    public Queue getOutcome() {
        return outcome;
    }

    public Queue getAborted() {
        return aborted;
    }

    public List<Queue> getQueues() {
        final List<Queue> queues = new ArrayList<>();
        for (final Service service : getServices()) {
            queues.add(service.getIncome());
        }
        return queues;
    }

    public List<Service> getServices() {
        return services;
    }

    public List<RequestType> getTypes() {
        return types;
    }

    public List<StatisticAggregator> getStatistics() {
        return statistics;
    }

    public List<Request> getGeneratedRequests() {
        return generated;
    }

    public List<Request> getProcessedRequests() {
        return outcome.getRequests();
    }

    public List<Request> getAbortedRequests() {
        return aborted.getRequests();
    }

    /**
     * Set statistics interval, negative value is for disabling statistics
     *
     * @param statisticsInterval to set
     */
    public void setStatisticsInterval(double statisticsInterval) {
        this.statisticsInterval = statisticsInterval;
    }

    public double getStatisticsInterval() {
        return statisticsInterval;
    }

    /**
     * Perform cleaning of the internal state and then modeling of the given time interval
     *
     * @param startTime start point of the time interval
     * @param stopTime  end point of the time interval
     */
    public void model(final double startTime, final double stopTime) {

        //clean up previous results
        reset();
        validate();

        final List<Queue> queues = getQueues();
        final PriorityQueue<RequestTypeHolder> typesQueue = new PriorityQueue<>();
        for (final RequestType type : types) {
            typesQueue.add(new RequestTypeHolder(type, type.getIncomeInterval().nextTime(startTime)));
        }

        //modeling itself
        double currentTime = startTime;
        double nextRequest = typesQueue.peek().getNextTime();
        double nextService;
        double nextStatistics = statisticsInterval < 0 ? Double.MAX_VALUE : currentTime;

        while (currentTime < stopTime) {
            if (equals(currentTime, nextRequest)) {
                final Request newRequest = nextRequest(typesQueue, currentTime);
                newRequest.getType().getQueueSelector().selectQueue(newRequest, income).addRequest(newRequest);
                newRequest.registerEvent(RequestEventType.ADDED_TO_THE_QUERY, currentTime);

                nextRequest = typesQueue.peek().getNextTime();
                generated.add(newRequest);
            }
            nextService = Double.MAX_VALUE;
            for (final Service s : services) {
                if (s.availableOn() < nextService && s.availableOn() > currentTime) {
                    nextService = s.availableOn();
                }
                if (equals(s.availableOn(), currentTime) && s.hasProcessing()) {
                    s.finishProcessOrAbort(currentTime, getAborted());
                }
                if (!s.isBusy(currentTime) && s.hasIncome()) {
                    s.startProcessOrAbort(currentTime, getAborted());
                }
            }
            if (equals(currentTime, nextStatistics)) {
                for (final StatisticAggregator aggregator : statistics) {
                    aggregator.addRecord(queues, getServices(), getGeneratedRequests(), getProcessedRequests(), getAbortedRequests());
                }
                nextStatistics += statisticsInterval;
            }
            currentTime = nextTime(nextRequest, nextService, nextStatistics);
        }
    }

    /**
     * Reset modeler internal state before next modeling.
     * This method does not remove structure (RequestTypes, Services, Queues, StatisticsAggregator),
     * but do remove Requests from Services, Queues and StatisticsAggregators
     */
    public void reset() {
        final List<Queue> queues = getQueues();

        //clean up previous results
        for (final Queue queue : queues) {
            queue.reset();
        }
        for (final Service service : services) {
            service.reset();
        }
        for (final StatisticAggregator aggregator : statistics) {
            aggregator.init(queues, services);
        }
        generated.clear();
        outcome.reset();
        aborted.reset();
    }

    /**
     * perform basic structure validation. Checks that RequestTypes and Services defined.
     * Do not validate queues and Services binding!
     */
    public void validate() {
        if (types.isEmpty()) {
            throw new ModelingException("No request types defined!");

        }
        if (services.isEmpty()) {
            throw new ModelingException("No services defined!");
        }
    }

    private static double nextTime(double nextRequest, double nextService, double nextStatistics) {
        return Math.min(nextRequest, Math.min(nextService, nextStatistics));
    }

    private static boolean equals(final double d1, final double d2) {
        return Math.abs(d1 - d2) < EPS;
    }

    private static Request nextRequest(final PriorityQueue<RequestTypeHolder> typesQueue, final double currentTime) {
        final RequestTypeHolder holder = typesQueue.poll();
        final Request newRequest = holder.getTypeAndUpdateTime().newRequest(currentTime);
        typesQueue.add(holder);
        return newRequest;
    }

    private static final double EPS = 0.001;
    private static final double DEFAULT_STATISTICS_INTERVAL = 60;

    public abstract static class SystemQueue implements Queue {

        private static final long serialVersionUID = 1L;

        private final List<Request> requests = new ArrayList<>();

        @Override
        public void addRequest(Request request) {
            requests.add(request);
        }

        @Override
        public Request getRequest() {
            return null;
        }

        @Override
        public int size() {
            return requests.size();
        }

        @Override
        public boolean isEmpty() {
            return requests.isEmpty();
        }

        @Override
        public boolean accepts(Request request) {
            return true;
        }

        @Override
        public void reset() {
            requests.clear();
        }

        public List<Request> getRequests() {
            return Collections.unmodifiableList(requests);
        }

        @Override
        public String toString() {
            return getName();
        }

    }

    public static class ProcessedQueue extends SystemQueue {

        @Override
        public String getName() {
            return "Processed" + Integer.toHexString(hashCode());
        }
    }

    public static class AbortedQueue extends SystemQueue {

        @Override
        public String getName() {
            return "Aborted" + Integer.toHexString(hashCode());
        }
    }

    private static final class RequestTypeHolder implements Comparable<RequestTypeHolder> {

        private final RequestType type;
        private double nextTime;

        private RequestTypeHolder(RequestType type, double nextTime) {
            this.type = type;
            this.nextTime = nextTime;
        }

        public double getNextTime() {
            return nextTime;
        }

        public RequestType getTypeAndUpdateTime() {
            this.nextTime = type.getIncomeInterval().nextTime(this.nextTime);
            return type;
        }

        @Override
        public int compareTo(RequestTypeHolder o) {
            return Double.compare(nextTime, o.nextTime);
        }
    }
}
