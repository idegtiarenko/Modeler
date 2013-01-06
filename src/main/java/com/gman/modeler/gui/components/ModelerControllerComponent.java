package com.gman.modeler.gui.components;

import javax.swing.*;

import com.gman.modeler.api.Modeler;
import com.gman.modeler.api.exceptions.ModelingException;
import com.gman.modeler.gui.api.DataModifier;
import com.gman.modeler.gui.components.util.JTimeInput;

/**
 * @author gman
 */
public class ModelerControllerComponent extends DataModifier {


    /**
     * Creates new form ModelerControllerComponent
     */
    public ModelerControllerComponent() {
        startTimeInput = new JTimeInput();
        stopTimeInput = new JTimeInput();
        statsTimeInput = new JTimeInput();
        initComponents();
    }

    @Override
    public String getComponentName() {
        return "Controller";
    }

    @Override
    public void setModeler(Modeler modeler) {
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modelButton = new javax.swing.JButton();
        propsPanel = new javax.swing.JPanel();
        labelsPanel = new javax.swing.JPanel();
        startLabel = new javax.swing.JLabel();
        stopLabel = new javax.swing.JLabel();
        statsLabel = new javax.swing.JLabel();
        timersPanel = new javax.swing.JPanel();
        resetButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        modelButton.setText("Model");
        modelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelButtonActionPerformed(evt);
            }
        });
        add(modelButton, java.awt.BorderLayout.CENTER);

        propsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        propsPanel.setLayout(new java.awt.BorderLayout());

        labelsPanel.setLayout(new java.awt.GridLayout(3, 1));

        startLabel.setText("Start");
        labelsPanel.add(startLabel);
        timersPanel.add(startTimeInput);

        stopLabel.setText("Stop");
        labelsPanel.add(stopLabel);
        timersPanel.add(stopTimeInput);

        statsLabel.setText("Statistics");
        labelsPanel.add(statsLabel);
        timersPanel.add(statsTimeInput);

        propsPanel.add(labelsPanel, java.awt.BorderLayout.LINE_START);

        timersPanel.setLayout(new java.awt.GridLayout(3, 1));
        propsPanel.add(timersPanel, java.awt.BorderLayout.CENTER);

        add(propsPanel, java.awt.BorderLayout.PAGE_START);

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        add(resetButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void modelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelButtonActionPerformed
        final Modeler modeler = getMediator().getModeler();
        modeler.setStatisticsInterval(statsTimeInput.getTime() > 0 ? statsTimeInput.getTime() : -1.0);
        try {
            if (startTimeInput.getTime() > stopTimeInput.getTime()) {
                throw new ModelingException("Stop time is before start time!");
            }
            modeler.model(startTimeInput.getTime(), stopTimeInput.getTime());
        } catch (final ModelingException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        getMediator().updateModeler(modeler);
    }//GEN-LAST:event_modelButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        final Modeler modeler = getMediator().getModeler();
        modeler.reset();
        getMediator().updateModeler(modeler);
    }//GEN-LAST:event_resetButtonActionPerformed

    private JTimeInput startTimeInput;
    private JTimeInput stopTimeInput;
    private JTimeInput statsTimeInput;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel labelsPanel;
    private javax.swing.JButton modelButton;
    private javax.swing.JPanel propsPanel;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel startLabel;
    private javax.swing.JLabel statsLabel;
    private javax.swing.JLabel stopLabel;
    private javax.swing.JPanel timersPanel;
    // End of variables declaration//GEN-END:variables
}
