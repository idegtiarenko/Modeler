package com.gman.modeler.queues.selectors;

import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.exceptions.NoSuchQueueException;

/**
 * Finds the shortest queue of all
 *
 * @author gman
 */
public class ShortestQueueSelector extends AbstractQueueSelector {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Queue selectQueue(final Request request, final List<Queue> queues) {
        final List<Queue> queuesToSelect = getAcceptedQueues(request, queues);
        int size = Integer.MAX_VALUE;
        Queue selected = null;
        for (final Queue q : queuesToSelect) {
            if (q.size() < size) {
                size = q.size();
                selected = q;
            }
        }
        if (selected == null) {
            throw new NoSuchQueueException();
        }
        return selected;
    }
}
