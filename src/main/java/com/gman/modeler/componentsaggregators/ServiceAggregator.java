package com.gman.modeler.componentsaggregators;

import com.gman.modeler.api.Service;
import com.gman.modeler.services.BatchService;
import com.gman.modeler.services.SimpleService;

/**
 * This aggregator register all algorithms for servicing queues
 *
 * @author gman
 */
public class ServiceAggregator extends AbstractComponentAggregator<Service> {

    /**
     * Initialize service and register all default algorithms
     */
    public ServiceAggregator() {
        registerComponent(SimpleService.class);
        registerComponent(BatchService.class);
    }
}
