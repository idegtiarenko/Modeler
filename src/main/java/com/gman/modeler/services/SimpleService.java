package com.gman.modeler.services;

import java.util.List;
import java.util.Map;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestProcessor;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.api.Timer;


/**
 * @author gman
 */
public class SimpleService extends AbstractService {

    private static final long serialVersionUID = 1L;

    private Request inProcess = null;

    public SimpleService(final String name,
                         final Queue income,
                         final List<Queue> outcome,
                         final Map<RequestType, Timer> timers,
                         final Map<RequestType, RequestProcessor> processors) {
        super(name, income, outcome, timers, processors);
    }

    @Override
    public void startProcessOrAbort(final double startProcessing, Queue abortQueue) {
        inProcess = income.getRequest();
        if (commonStartProcessing(inProcess, startProcessing, abortQueue)) {
            availableOn = calculateAvailableOn(inProcess.getType(), startProcessing);
        } else {
            inProcess = null;
            availableOn = startProcessing;
        }
    }

    @Override
    public void finishProcessOrAbort(double finishProcessing, Queue abortQueue) {
        commonFinishProcessing(inProcess, finishProcessing);
        inProcess = null;
    }

    @Override
    public boolean hasProcessing() {
        return inProcess != null;
    }

    @Override
    public void reset() {
        super.reset();
        this.inProcess = null;
    }
}
