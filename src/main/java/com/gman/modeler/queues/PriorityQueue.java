package com.gman.modeler.queues;

import java.util.Comparator;
import java.util.Set;

import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestType;

/**
 * TODO Think on it
 *
 * @author gman
 */
public class PriorityQueue extends AbstractQueue {

    private static final long serialVersionUID = 1L;

    private final java.util.PriorityQueue<Request> queue;

    /**
     * Creates queue with given accepted types
     *
     * @param accept     accepted types
     * @param comparator comparator to determine priority
     */
    public PriorityQueue(final Set<RequestType> accept, final Comparator<Request> comparator) {
        super(accept);
        queue = new java.util.PriorityQueue<>(1, comparator);
    }

    @Override
    public void addRequest(final Request request) {
        queue.add(request);
    }

    @Override
    public Request getRequest() {
        return queue.poll();
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
