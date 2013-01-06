/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gman.modeler.queues;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.exceptions.ModelingException;

/**
 * @author gman
 */
public class LimitedQueueProxy implements Queue {

    private static final long serialVersionUID = 1L;

    private final Queue underlying;
    private final int limit;

    public LimitedQueueProxy(Queue underlying, int limit) {
        this.underlying = underlying;
        this.limit = limit;
    }

    public Queue getUnderlying() {
        return underlying;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void addRequest(Request request) {
        if (underlying.size() + 1 >= limit) {
            throw new LimitedQueryException();
        }
        underlying.addRequest(request);
    }

    @Override
    public Request getRequest() {
        return underlying.getRequest();
    }

    @Override
    public int size() {
        return underlying.size();
    }

    @Override
    public boolean isEmpty() {
        return underlying.isEmpty();
    }

    @Override
    public boolean accepts(Request request) {
        return underlying.accepts(request);
    }

    @Override
    public String getName() {
        return "Limited proxy of " + underlying.getName();
    }

    @Override
    public void reset() {
    }

    @Override
    public String toString() {
        return getName();
    }

    public static class LimitedQueryException extends ModelingException {

        public LimitedQueryException() {
        }
    }
}
