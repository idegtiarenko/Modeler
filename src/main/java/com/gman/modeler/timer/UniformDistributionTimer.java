package com.gman.modeler.timer;

import java.util.Random;

import com.gman.modeler.api.TimeUtils;
import com.gman.modeler.api.Timer;

/**
 * This class will produce uniform distributed time intervals
 *
 * @author gman
 */
public class UniformDistributionTimer implements Timer {

    private static final long serialVersionUID = 1L;

    private final double min;
    private final double range;
    private final Random rand;

    /**
     * Default uniform time interval [1 .. 2)
     */
    public UniformDistributionTimer() {
        this(1.0, 1.0);
    }

    /**
     * Construct uniform time interval [min .. min+range)
     *
     * @param min   minimal time to be returned
     * @param range min+range is maximal time to be returned
     */
    public UniformDistributionTimer(final double min, final double range) {
        this.min = min;
        this.range = range;
        this.rand = new Random();
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return min + range;
    }

    @Override
    public double nextTime(double currentTime) {
        return currentTime + min + range * rand.nextDouble();
    }

    @Override
    public String toString() {
        return String.format("Uniform(%s, %s)", TimeUtils.timeString(min), TimeUtils.timeString(min + range));
    }
}
