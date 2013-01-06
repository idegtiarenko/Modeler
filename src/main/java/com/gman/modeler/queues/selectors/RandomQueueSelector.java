package com.gman.modeler.queues.selectors;

import java.util.List;
import java.util.Random;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.exceptions.NoSuchQueueException;

/**
 * Selects random queue
 *
 * @author gman
 */
public class RandomQueueSelector extends AbstractQueueSelector {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Queue selectQueue(final Request request, final List<Queue> queues) {
        final List<Queue> queuesToSelect = getAcceptedQueues(request, queues);
        if (queuesToSelect.isEmpty()) {
            throw new NoSuchQueueException();
        }
        final int selected = new Random().nextInt(queuesToSelect.size());
        return queuesToSelect.get(selected);
    }
}
