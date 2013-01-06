package com.gman.modeler.api;

import java.io.Serializable;

/**
 * This interface describe action that is applied to Request while passing service
 *
 * @author gman
 */
public interface RequestProcessor extends Serializable {

    /**
     * Apply action on Request
     *
     * @param request is the request that is processing
     */
    void processRequest(final Request request);
}