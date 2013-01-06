package com.gman.modeler.timer;

import com.gman.modeler.api.TimeUtils;
import com.gman.modeler.api.Timer;

/**
 * This class will produce constant time intervals
 *
 * @author gman
 */
public class ConstantTimer implements Timer {

    private static final long serialVersionUID = 1L;

    private final double interval;

    /**
     * Default constant time interval (1 second)
     */
    public ConstantTimer() {
        this(1.0);
    }

    /**
     * Creates custom constant time intervals
     *
     * @param interval is the time interval
     */
    public ConstantTimer(double interval) {
        this.interval = interval;
    }

    public double getInterval() {
        return interval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double nextTime(double currentTime) {
        return currentTime + interval;
    }

    @Override
    public String toString() {
        return String.format("Constant(%s)", TimeUtils.timeString(interval));
    }
}
