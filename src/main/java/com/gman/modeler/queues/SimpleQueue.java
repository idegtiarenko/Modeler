package com.gman.modeler.queues;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestType;

/**
 * Simple implementation of the queue
 *
 * @author gman
 */
public class SimpleQueue extends AbstractQueue {

    private static final long serialVersionUID = 1L;

    protected final Deque<Request> queue = new LinkedList<>();

    /**
     * Creates queue with given accepted types
     *
     * @param accept accepted types
     */
    public SimpleQueue(final Set<RequestType> accept) {
        super(accept);
    }

    @Override
    public void addRequest(final Request request) {
        assertAcceptable(request.getType());
        queue.addFirst(request);
    }

    @Override
    public Request getRequest() {
        return queue.pollLast();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void reset() {
        queue.clear();
    }
}
