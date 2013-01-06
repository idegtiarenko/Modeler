package com.gman.modeler.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestProcessor;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.api.Timer;


/**
 * @author gman
 * @since 06.05.12 17:05
 */
public class BatchService extends AbstractService {

    private static final long serialVersionUID = 1L;

    protected final List<Request> inProcess = new ArrayList<>();
    protected final int batchSize;

    public BatchService(final String name,
                        final Queue income,
                        final List<Queue> outcome,
                        final Map<RequestType, Timer> timers,
                        final Map<RequestType, RequestProcessor> processors,
                        final int batchSize) {
        super(name, income, outcome, timers, processors);
        this.batchSize = batchSize;
    }

    @Override
    public void startProcessOrAbort(double startProcessing, Queue abortQueue) {
        inProcess.add(income.getRequest());
        if (inProcess.size() == batchSize) {
            double timeToProcess = 0;
            for (final Request request : inProcess) {
                timeToProcess += calculateAvailableOn(request.getType(), startProcessing) - startProcessing;
            }
            availableOn = startProcessing + timeToProcess;
            for (final Request request : inProcess) {
                if (!commonStartProcessing(request, startProcessing, abortQueue)) {
                    inProcess.remove(request);
                }
            }
        }
    }

    @Override
    public void finishProcessOrAbort(double finishProcessing, Queue abortQueue) {
        for (final Request request : inProcess) {
            commonFinishProcessing(request, finishProcessing);
        }
        inProcess.clear();
    }

    @Override
    public boolean hasProcessing() {
        return !inProcess.isEmpty();
    }

    @Override
    public void reset() {
        super.reset();
        this.inProcess.clear();
    }

    public int getBatchSize() {
        return batchSize;
    }
}
