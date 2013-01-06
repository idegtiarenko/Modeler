package com.gman.modeler.api;

import java.io.Serializable;

/**
 * Type is responsible for event kind.
 * It can describe creation time intervals, default processing time intervals and abort interval
 *
 * @author gman
 */
public class RequestType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestTypeName;

    private QueueSelector queueSelector;
    private Timer processingInterval;
    private Timer incomeInterval;
    private Timer abortInterval;

    /**
     * @return name of the type of the request
     */
    public String getName() {
        return requestTypeName;
    }

    /**
     * @param requestTypeName name of the type of the request
     */
    public void setName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

    /**
     * @return default algorithm for selecting query for current RequestType (may be override in exact request?)
     */
    public QueueSelector getQueueSelector() {
        return queueSelector;
    }

    /**
     * @param queueSelector default algorithm for selecting query for current RequestType (may be overridden in exact request?)
     */
    public void setQueueSelector(QueueSelector queueSelector) {
        this.queueSelector = queueSelector;
    }

    /**
     * @return default processing time interval for this type of requests (may be overridden in exact Service)
     */
    public Timer getProcessingInterval() {
        return processingInterval;
    }

    /**
     * @param processingInterval default algorithm for selecting query for current RequestType (may be override in exact request?)
     */
    public void setProcessingInterval(Timer processingInterval) {
        this.processingInterval = processingInterval;
    }

    /**
     * @return time interval of incoming events of this type
     */
    public Timer getIncomeInterval() {
        return incomeInterval;
    }

    /**
     * @param incomeInterval time interval of incoming events of this type
     */
    public void setIncomeInterval(Timer incomeInterval) {
        this.incomeInterval = incomeInterval;
    }

    /**
     * @return time before abort
     */
    public Timer getAbortInterval() {
        return abortInterval;
    }

    /**
     * @param abortInterval time before abort
     */
    public void setAbortInterval(Timer abortInterval) {
        this.abortInterval = abortInterval;
    }

    /**
     * Instantiate new request
     *
     * @param currentTime time of creation
     * @return new event of this type
     */
    public Request newRequest(final double currentTime) {
        return new Request(currentTime, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RequestType that = (RequestType) o;

        if (requestTypeName != null) {
            return this.requestTypeName.equals(that.requestTypeName);
        } else {
            return that.requestTypeName == null;
        }
    }

    @Override
    public int hashCode() {
        return requestTypeName != null ? requestTypeName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getName();
    }
}
