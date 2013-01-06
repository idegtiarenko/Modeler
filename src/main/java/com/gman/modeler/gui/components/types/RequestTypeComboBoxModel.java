package com.gman.modeler.gui.components.types;

import javax.swing.*;

import com.gman.modeler.api.RequestType;
import com.gman.modeler.gui.api.DataMediator;

/**
 * @author gman
 */
public class RequestTypeComboBoxModel extends DefaultComboBoxModel<String> {

    private final DataMediator mediator;

    public RequestTypeComboBoxModel(DataMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public int getSize() {
        return mediator.getModeler().getTypes().size();
    }

    @Override
    public String getElementAt(int index) {
        return mediator.getModeler().getTypes().get(index).getName();
    }

    public RequestType getRequestTypeAt(int index) {
        return mediator.getModeler().getTypes().get(index);
    }
}
