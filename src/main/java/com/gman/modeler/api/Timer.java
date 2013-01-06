package com.gman.modeler.api;

import java.io.Serializable;

/**
 * This interface generates next time interval based on current time
 * This means next time interval may or may not depend on current time
 *
 * @author gman
 */
public interface Timer extends Serializable {

    /**
     * generate next time moment dependently on current
     *
     * @param currentTime current time
     * @return next time interval
     */
    double nextTime(final double currentTime);
}