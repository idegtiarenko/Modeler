package com.gman.modeler.queues;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.api.exceptions.NotAllowedRequestTypeException;

/**
 * This is a common part of any default queue
 *
 * @author gman
 */
public abstract class AbstractQueue implements Queue {

    protected final Set<RequestType> accept = new HashSet<>();

    /**
     * Creates queue with given accepted types
     *
     * @param accept
     */
    protected AbstractQueue(final Set<RequestType> accept) {
        this.accept.addAll(accept);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean accepts(final Request request) {
        return accept.contains(request.getType());
    }

    @Override
    public String getName() {
        return getClass().getSimpleName() + Integer.toHexString(hashCode());
    }

    /**
     * Obtains set of Request types that is accepted by this queue
     *
     * @return accepted request types
     */
    public Set<RequestType> getAccept() {
        return Collections.unmodifiableSet(accept);
    }

    protected void assertAcceptable(final RequestType requestType) {
        if (!accept.contains(requestType)) {
            throw new NotAllowedRequestTypeException();
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
