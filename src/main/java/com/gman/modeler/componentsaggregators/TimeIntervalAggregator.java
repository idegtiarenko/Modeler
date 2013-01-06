package com.gman.modeler.componentsaggregators;

import com.gman.modeler.api.Timer;
import com.gman.modeler.timer.ConstantTimer;
import com.gman.modeler.timer.NormalDistributionTimer;
import com.gman.modeler.timer.UniformDistributionTimer;

/**
 * This aggregator register all algorithms for producing time intervals
 *
 * @author gman
 */
public class TimeIntervalAggregator extends AbstractComponentAggregator<Timer> {

    /**
     * Initialize service and register all default algorithms
     */
    public TimeIntervalAggregator() {
        registerComponent(ConstantTimer.class);
        registerComponent(UniformDistributionTimer.class);
        registerComponent(NormalDistributionTimer.class);
    }
}