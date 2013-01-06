package com.gman.modeler.api;

import java.io.Serializable;

/**
 * Represents event with type and moment of time
 *
 * @author gman
 */
public class RequestEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RequestEventType modelingEventType;
    private final double time;

    /**
     * Constructs new event
     *
     * @param modelingEventType the type
     * @param time              the time
     */
    public RequestEvent(final RequestEventType modelingEventType, final double time) {
        this.modelingEventType = modelingEventType;
        this.time = time;
    }

    /**
     * obtains type of the event
     *
     * @return type of the event
     */
    public RequestEventType getModelingEventType() {
        return modelingEventType;
    }

    /**
     * obtains time of the event
     *
     * @return time of the event
     */
    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append(modelingEventType).
                append('(').append(TimeUtils.timeString(time)).append(')').
                toString();
    }
}
