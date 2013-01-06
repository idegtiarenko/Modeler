package com.gman.modeler.gui.api;

import javax.swing.*;

import com.gman.modeler.api.Modeler;

/**
 * @author gman
 */
public abstract class DataListener extends JPanel {

    public abstract void setModeler(Modeler modeler);

    public abstract String getComponentName();
}
