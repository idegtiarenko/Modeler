/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gman.modeler.gui.components.util;

import javax.swing.*;

import com.gman.modeler.api.TimeUtils;

/**
 * @author gman
 */
public class JTimeInput extends JPanel {

    /**
     * Creates new form JTimeInput
     */
    public JTimeInput() {
        initComponents();
    }

    public void setTime(double time) {
        final int[] components = TimeUtils.timeToComponents(time);
        hoursSpinner.setValue(components[TimeUtils.HOUR]);
        minutesSpinner.setValue(components[TimeUtils.MINUTE]);
        secondsSpinner.setValue(components[TimeUtils.SECOND]);
        millisSpinner.setValue(components[TimeUtils.MILLI]);
    }

    public double getTime() {
        final int[] components = new int[TimeUtils.MILLI + 1];
        components[TimeUtils.HOUR] = (Integer) hoursSpinner.getValue();
        components[TimeUtils.MINUTE] = (Integer) minutesSpinner.getValue();
        components[TimeUtils.SECOND] = (Integer) secondsSpinner.getValue();
        components[TimeUtils.MILLI] = (Integer) millisSpinner.getValue();
        return TimeUtils.timeFromComponents(components);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hoursSpinner = new javax.swing.JSpinner();
        minutesSpinner = new javax.swing.JSpinner();
        secondsSpinner = new javax.swing.JSpinner();
        millisSpinner = new javax.swing.JSpinner();

        setLayout(new java.awt.GridLayout(1, 4, 3, 0));

        hoursSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));
        add(hoursSpinner);

        minutesSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        add(minutesSpinner);

        secondsSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        add(secondsSpinner);

        millisSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));
        add(millisSpinner);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner hoursSpinner;
    private javax.swing.JSpinner millisSpinner;
    private javax.swing.JSpinner minutesSpinner;
    private javax.swing.JSpinner secondsSpinner;
    // End of variables declaration//GEN-END:variables
}