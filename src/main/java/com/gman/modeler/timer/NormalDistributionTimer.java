package com.gman.modeler.timer;

import java.util.Random;

import com.gman.modeler.api.TimeUtils;
import com.gman.modeler.api.Timer;

/**
 * This class will produce normal distributed time intervals
 *
 * @author gman
 */
public class NormalDistributionTimer implements Timer {

    private static final long serialVersionUID = 1L;

    private final double mean;
    private final double sigma;
    private final Random rand;

    /**
     * Default normal distribution with params (0, 1)
     */
    public NormalDistributionTimer() {
        this(0, 1);
    }

    /**
     * Construct normal distributed time interval that greater than 0
     *
     * @param mean  is the argument of the distribution
     * @param sigma min+range is the argument of the distribution
     */
    public NormalDistributionTimer(final double mean, final double sigma) {
        this.mean = mean;
        this.sigma = sigma;
        this.rand = new Random();
    }

    public double getMean() {
        return mean;
    }

    public double getSigma() {
        return sigma;
    }

    @Override
    public double nextTime(final double currentTime) {
        return currentTime + Math.max(0, mean + rand.nextGaussian() * sigma);
    }

    @Override
    public String toString() {
        return String.format("Normal(%, %.4f)", mean, sigma);
    }
}
