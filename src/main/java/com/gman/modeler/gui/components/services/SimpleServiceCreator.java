package com.gman.modeler.gui.components.services;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.RequestProcessor;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.gui.api.DataCreator;
import com.gman.modeler.gui.api.DataMediator;
import com.gman.modeler.gui.components.util.RequestTypeTimerEditor;
import com.gman.modeler.services.SimpleService;

/**
 * @author gman
 */
public class SimpleServiceCreator extends DataCreator<SimpleService> {

    /**
     * Creates new form SimpleServiceCreator
     */
    public SimpleServiceCreator(DataMediator mediator) {
        editor = new RequestTypeTimerEditor(mediator);
        initComponents();
        add(editor, BorderLayout.CENTER);
    }

    @Override
    public void reset() {
        nameTextField.setText("");
        editor.reset();
    }

    @Override
    public void parse(SimpleService obj) {
        nameTextField.setText(obj.getName());
        editor.parse(obj);
    }

    @Override
    public SimpleService create() {
        return new SimpleService(
                nameTextField.getText(),
                null,
                new ArrayList<Queue>(),
                editor.create(),
                new HashMap<RequestType, RequestProcessor>());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        namePanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        namePanel.setLayout(new java.awt.GridLayout(1, 2));

        nameLabel.setText("Name");
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);

        add(namePanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private RequestTypeTimerEditor editor;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables
}