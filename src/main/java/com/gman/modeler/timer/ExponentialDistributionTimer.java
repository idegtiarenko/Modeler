package com.gman.modeler.timer;

import com.gman.modeler.api.Timer;

import java.util.Random;

/**
 * @author gman
 * @since 16.09.12 22:45
 */
public class ExponentialDistributionTimer implements Timer {

    private static final long serialVersionUID = 1L;

    private final double lamda;
    private final Random rand;

    public ExponentialDistributionTimer() {
        this(1);
    }

    public ExponentialDistributionTimer(double lamda) {
        this.lamda = lamda;
        this.rand = new Random();
    }

    public double getLamda() {
        return lamda;
    }

    @Override
    public double nextTime(double currentTime) {
        return currentTime - (1.0 /(lamda * Math.log(1-rand.nextDouble())));
    }

    @Override
    public String toString() {
        return String.format("Exponential(%.4f)", lamda);
    }
}
