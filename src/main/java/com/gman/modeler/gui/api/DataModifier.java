package com.gman.modeler.gui.api;

/**
 * @author gman
 */
public abstract class DataModifier extends DataListener {

    private DataMediator mediator;

    protected DataMediator getMediator() {
        return mediator;
    }

    public void initCallBack(DataMediator mediator) {
        this.mediator = mediator;
    }

    public void destroyCallBack(DataMediator mediator) {
        this.mediator = null;
    }
}
