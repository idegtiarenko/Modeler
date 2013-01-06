package com.gman.modeler.queues;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;

/**
 * @author gman
 * @since 4/10/12 11:18 AM
 */
public class StatisticsQueueProxy implements Queue {

    private static final long serialVersionUID = 1L;

    protected final Queue underlying;
    protected int maxSize = 0;
    protected double avgSize = 0;
    protected int trafficSize = 0;

    public StatisticsQueueProxy(Queue underlying) {
        this.underlying = underlying;
    }

    public Queue getUnderlying() {
        return underlying;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public double getAvgSize() {
        return avgSize;
    }

    public int getTrafficSize() {
        return trafficSize;
    }

    @Override
    public void addRequest(Request request) {
        underlying.addRequest(request);
        avgSize = (trafficSize * avgSize + underlying.size()) / (trafficSize + 1);
        trafficSize++;
        if (underlying.size() > maxSize) {
            maxSize = underlying.size();
        }
    }

    @Override
    public Request getRequest() {
        return underlying.getRequest();
    }

    @Override
    public int size() {
        return underlying.size();
    }

    @Override
    public boolean isEmpty() {
        return underlying.isEmpty();
    }

    @Override
    public boolean accepts(Request request) {
        return underlying.accepts(request);
    }

    @Override
    public String getName() {
        return "Stat proxy of " + underlying.getName();
    }

    @Override
    public void reset() {
        underlying.reset();
        maxSize = 0;
        avgSize = 0;
        trafficSize = 0;
    }

    @Override
    public String toString() {
        return getName();
    }
}
