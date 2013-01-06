package com.gman.modeler.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Is the request representation
 * Holds its type and events
 *
 * @author gman
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private RequestType type;
    private double timeToAbort;
    private final List<RequestEvent> events = new ArrayList<>();

    /**
     * Creates request of type in given time
     *
     * @param timeCreation is the time of creation
     * @param type         is the request type
     */
    public Request(final double timeCreation, final RequestType type) {
        this.type = type;
        this.timeToAbort = type.getAbortInterval().nextTime(timeCreation);
        events.add(new RequestEvent(RequestEventType.CREATED, timeCreation));
    }

    /**
     * Register event
     *
     * @param event is the event to register
     */
    public void registerEvent(final RequestEvent event) {
        events.add(event);
    }

    /**
     * Create and register event
     *
     * @param type is the event type
     * @param time is the time of the event
     */
    public void registerEvent(final RequestEventType type, final double time) {
        registerEvent(new RequestEvent(type, time));
    }

    /**
     * Modify request type
     *
     * @param type is the new type
     */
    public void setType(final RequestType type) {
        events.add(new RequestEvent(RequestEventType.CHANGED_TYPE, -1));
        this.type = type;
    }

    /**
     * Calculate time lasted for the specific event
     *
     * @param type is the event type
     * @return is the time for the event
     */
    public double getFullTimeForEvent(final RequestEventType type) {
        double time = 0;
        boolean summing = false;
        for (final RequestEvent event : events) {
            if (summing) {
                summing = false;
                time -= event.getTime();
            }
            if (event.getModelingEventType() == type) {
                summing = true;
                time += event.getTime();
            }
        }
        return -time;
    }

    /**
     * Calculates full time of the life cycle of the request
     *
     * @return life time
     */
    public double getFullTime() {
        final double endTime = events.get(events.size() - 1).getTime();
        final double beginTime = events.get(0).getTime();
        return endTime - beginTime;
    }

    /**
     * Return the time when request is no more actual and may be aborted
     *
     * @return abortion time
     */
    public double getTimeToAbort() {
        return timeToAbort;
    }

    /**
     * Obtain list of the events
     *
     * @return is the list of events
     */
    public List<RequestEvent> getEvents() {
        return events;
    }

    /**
     * Obtain request type
     *
     * @return type
     */
    public RequestType getType() {
        return type;
    }
}