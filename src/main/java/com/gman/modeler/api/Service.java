package com.gman.modeler.api;

import java.io.Serializable;
import java.util.List;

/**
 * This interface represents service that perform processing.
 * Must income at least one queue and outcome at least one or have end point
 *
 * @author gman
 */
public interface Service extends Serializable {

    /**
     * Perform start processing next request from the queue
     *
     * @param startProcessing time of start processing
     * @param abortQueue      is the queue for aborts
     */
    void startProcessOrAbort(final double startProcessing, Queue abortQueue);

    /**
     * Perform release last processed request(s) from the queue
     *
     * @param finishProcessing
     * @param abortQueue       is the queue for aborts
     */
    void finishProcessOrAbort(final double finishProcessing, Queue abortQueue);

    /**
     * Return {@code true} if has processing request or {@code false} otherwise
     *
     * @return {@code true} if has processing request or {@code false} otherwise
     */
    boolean hasProcessing();

    /**
     * Check is service available on given time
     *
     * @param currentTime given time
     * @return {@code true} if available on given time or {@code false} if not
     */
    boolean isBusy(final double currentTime);

    /**
     * Obtain time when service will be available
     *
     * @return moment of time when available
     */
    double availableOn();

    /**
     * Determine if there is income requests for this service
     *
     * @return {@code true if there is at least one request}
     */
    boolean hasIncome();

    /**
     * Obtain income
     *
     * @return income queue to the service
     */
    Queue getIncome();

    /**
     * Update income
     *
     * @param queue is the new income
     */
    void setIncome(Queue queue);

    /**
     * Obtain outcomes
     *
     * @return outcomes of the service
     */
    List<Queue> getOutcome();

    /**
     * Obtain name of the service instance
     *
     * @return the name
     */
    String getName();

    /**
     * Clear internal state
     */
    void reset();
}
