package com.gman.modeler.componentsaggregators;

import com.gman.modeler.api.RequestProcessor;
import com.gman.modeler.requestprocessors.DummyRequestProcessor;
import com.gman.modeler.requestprocessors.TypeModifierRequestProcessor;

/**
 * This aggregator register all algorithms for processing requests
 *
 * @author gman
 */
public class RequestProcessorAggregator extends AbstractComponentAggregator<RequestProcessor> {

    /**
     * Initialize service and register all default algorithms
     */
    public RequestProcessorAggregator() {
        registerComponent(DummyRequestProcessor.class);
        registerComponent(TypeModifierRequestProcessor.class);
    }
}