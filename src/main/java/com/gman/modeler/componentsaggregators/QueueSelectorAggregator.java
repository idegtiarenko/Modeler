package com.gman.modeler.componentsaggregators;

import com.gman.modeler.api.QueueSelector;
import com.gman.modeler.queues.selectors.RandomQueueSelector;
import com.gman.modeler.queues.selectors.ShortestQueueSelector;

/**
 * This aggregator register all algorithms for selecting queues
 *
 * @author gman
 */
public class QueueSelectorAggregator extends AbstractComponentAggregator<QueueSelector> {

    /**
     * Initialize service and register all default algorithms
     */
    public QueueSelectorAggregator() {
        registerComponent(RandomQueueSelector.class);
        registerComponent(ShortestQueueSelector.class);
    }
}