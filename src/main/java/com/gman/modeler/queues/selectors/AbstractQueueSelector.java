package com.gman.modeler.queues.selectors;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.QueueSelector;
import com.gman.modeler.api.Request;

/**
 * Provide common functionality for queue selectors
 *
 * @author gman
 * @since 29.04.12 19:06
 */
public abstract class AbstractQueueSelector implements QueueSelector {

    private static final long serialVersionUID = 1L;

    /**
     * Remove queues from list that does not accept given request
     *
     * @param request is the request to accept
     * @param queues  is the queues list
     * @return filtered list
     */
    protected List<Queue> getAcceptedQueues(final Request request, final List<Queue> queues) {
        final List<Queue> accept = new ArrayList<>();
        for (final Queue q : queues) {
            if (q.accepts(request)) {
                accept.add(q);
            }
        }
        return accept;
    }
}
