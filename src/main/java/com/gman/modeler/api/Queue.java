package com.gman.modeler.api;

import java.io.Serializable;

/**
 * Represents queue of requests.
 * Queue may contain only specific RequestTypes.
 * Queue may be used by single service of shared across several
 *
 * @author gman
 */
public interface Queue extends Serializable {

    /**
     * Add request to the queue
     *
     * @param request to add
     */
    void addRequest(final Request request);

    /**
     * Get next request to process
     *
     * @return request to process
     */
    Request getRequest();

    /**
     * Obtain number of requests in the queue
     *
     * @return size of the queue
     */
    int size();

    /**
     * Determines if there is no requests in the queue
     *
     * @return {@code true} if there is no requests in the queue or {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Determines weather given request could be added to the queue
     *
     * @param request to enqueue
     * @return {@code true} if request can be added to the queue or {@code false} otherwise
     */
    boolean accepts(final Request request);

    /**
     * Obtains current queues name
     *
     * @return the name of the queue
     */
    String getName();

    /**
     * Clear internal state
     */
    void reset();
}
