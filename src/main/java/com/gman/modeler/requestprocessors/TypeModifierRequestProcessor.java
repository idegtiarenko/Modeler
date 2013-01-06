package com.gman.modeler.requestprocessors;

import java.util.HashMap;
import java.util.Map;

import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestProcessor;
import com.gman.modeler.api.RequestType;

/**
 * This class change RequestType of the request while processing
 *
 * @author gman
 */
public class TypeModifierRequestProcessor implements RequestProcessor {

    private static final long serialVersionUID = 1L;

    private final Map<RequestType, RequestType> modifiers = new HashMap<RequestType, RequestType>();

    /**
     * Construct default processor with no modifying ruler
     */
    public TypeModifierRequestProcessor() {
    }

    /**
     * Construct processor with given rulers
     *
     * @param modifiers
     */
    public TypeModifierRequestProcessor(final Map<RequestType, RequestType> modifiers) {
        this.modifiers.putAll(modifiers);
    }

    /**
     * Rulers accessor to modify them
     *
     * @return map of modifying rulers
     */
    public Map<RequestType, RequestType> getModifiers() {
        return modifiers;
    }

    @Override
    public void processRequest(final Request request) {
        final RequestType oldType = request.getType();
        final RequestType newType = modifiers.get(oldType);
        if (newType != null) {
            request.setType(newType);
        }
    }
}
