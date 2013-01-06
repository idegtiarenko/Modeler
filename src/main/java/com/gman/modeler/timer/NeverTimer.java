package com.gman.modeler.timer;

import com.gman.modeler.api.Timer;

/**
 * This class will provide timer that never occurs
 *
 * @author gman
 * @since 29.04.12 16:47
 */
public class NeverTimer implements Timer {

    private static final long serialVersionUID = 1L;

    @Override
    public double nextTime(double currentTime) {
        return Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        return String.format("Never()");
    }
}
