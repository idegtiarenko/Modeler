package com.gman.modeler.api;


import java.io.Serializable;
import java.util.List;

/**
 * This interface select next appropriate queue from a list of given to proceed
 *
 * @author gman
 */
public interface QueueSelector extends Serializable {

    /**
     * Selects appropriate queue from given list or throw an {@link com.gman.modeler.api.exceptions.NoSuchQueueException}
     * if can not select or list is empty
     *
     * @param request request that selects a queue
     * @param queues  list of alternatives
     * @return selected queue
     * @throws com.gman.modeler.api.exceptions.NoSuchQueueException
     *          if queues is empty or can not select queue
     */
    Queue selectQueue(final Request request, final List<Queue> queues);
}
