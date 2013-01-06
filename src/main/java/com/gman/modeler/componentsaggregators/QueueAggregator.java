package com.gman.modeler.componentsaggregators;

import com.gman.modeler.api.Queue;
import com.gman.modeler.queues.PriorityQueue;
import com.gman.modeler.queues.SimpleQueue;

/**
 * This aggregator register all algorithms for organizing queues of requests
 *
 * @author gman
 */
public class QueueAggregator extends AbstractComponentAggregator<Queue> {

    /**
     * Initialize service and register all default algorithms
     */
    public QueueAggregator() {
        registerComponent(SimpleQueue.class);
        registerComponent(PriorityQueue.class);
    }
}