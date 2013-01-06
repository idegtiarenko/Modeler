package com.gman.modeler.queues;

import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestType;

import java.util.Set;

/**
 * @author gman
 * @since 16.09.12 20:37
 */
public class SimpleInvertedQueue extends SimpleQueue {

    public SimpleInvertedQueue(Set<RequestType> accept) {
        super(accept);
    }

    @Override
    public Request getRequest() {
        return queue.pollFirst();
    }
}
