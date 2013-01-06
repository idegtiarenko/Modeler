package com.gman.modeler.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gman.modeler.api.Modeler;
import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestEventType;
import com.gman.modeler.api.RequestProcessor;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.Timer;

/**
 * Common processing code
 *
 * @author gman
 */
public abstract class AbstractService implements Service {

    private static final long serialVersionUID = 1L;

    protected final String name;
    protected Queue income;
    protected final List<Queue> outcome = new ArrayList<>();
    protected final Map<RequestType, Timer> timers = new HashMap<>();
    protected final Map<RequestType, RequestProcessor> processors = new HashMap<>();

    protected double availableOn = 0;

    protected AbstractService(final String name,
                              final com.gman.modeler.api.Queue income,
                              final List<Queue> outcome,
                              final Map<RequestType, Timer> timers,
                              final Map<RequestType, RequestProcessor> processors) {
        this.name = name;
        this.income = income;
        this.outcome.addAll(outcome);
        this.timers.putAll(timers);
        this.processors.putAll(processors);
    }

    @Override
    public double availableOn() {
        return availableOn;
    }

    @Override
    public boolean isBusy(double currentTime) {
        return currentTime < availableOn;
    }

    @Override
    public boolean hasIncome() {
        return !income.isEmpty();
    }

    @Override
    public Queue getIncome() {
        return income;
    }

    @Override
    public void setIncome(Queue income) {
        this.income = income;
    }

    @Override
    public List<Queue> getOutcome() {
        return outcome;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void reset() {
        this.availableOn = 0;
    }

    public Map<RequestType, Timer> getTimers() {
        return timers;
    }

    protected boolean commonStartProcessing(final Request request, double startProcessing, final Queue abortQueue) {
        request.registerEvent(RequestEventType.REMOVED_FROM_THE_QUERY, startProcessing);
        if (request.getTimeToAbort() > startProcessing) {
            request.registerEvent(RequestEventType.STARTED_PROCESSING, startProcessing);
            final RequestProcessor processor = processors.get(request.getType());
            if (processor != null) {
                processor.processRequest(request);
            }
            return true;
        } else {
            request.registerEvent(RequestEventType.ABORTED, request.getTimeToAbort());
            abortQueue.addRequest(request);
            return false;
        }
    }

    protected void commonFinishProcessing(final Request request, final double finishProcessing) {
        request.registerEvent(RequestEventType.FINISHED_PROCESSING, finishProcessing);
        final Queue queue = request.getType().getQueueSelector().selectQueue(request, outcome);
        if (queue instanceof Modeler.ProcessedQueue) {
            request.registerEvent(RequestEventType.RELEASED, finishProcessing);
        } else {
            request.registerEvent(RequestEventType.ADDED_TO_THE_QUERY, finishProcessing);
        }
        queue.addRequest(request);
    }

    protected double calculateAvailableOn(final RequestType requestType, final double startProcessing) {
        if (timers.get(requestType) != null) {
            return timers.get(requestType).nextTime(startProcessing);
        } else {
            return requestType.getProcessingInterval().nextTime(startProcessing);
        }
    }
}
