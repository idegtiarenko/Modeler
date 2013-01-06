package com.gman.modeler.requestprocessors;

import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestProcessor;

/**
 * This processor do nothing with request
 *
 * @author gman
 */
public class DummyRequestProcessor implements RequestProcessor {

    private static final long serialVersionUID = 1L;

    @Override
    public void processRequest(final Request request) {
        //do nothing
    }
}
