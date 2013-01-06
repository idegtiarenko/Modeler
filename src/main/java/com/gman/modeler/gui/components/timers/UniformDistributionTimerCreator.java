package com.gman.modeler.gui.components.timers;

import com.gman.modeler.gui.api.DataCreator;
import com.gman.modeler.timer.UniformDistributionTimer;

/**
 * @author gman
 */
public class UniformDistributionTimerCreator extends DataCreator<UniformDistributionTimer> {

    /**
     * Creates new form UniformDistributionTimerCreator
     */
    public UniformDistributionTimerCreator() {
        initComponents();
    }

    @Override
    public void reset() {
        minIntervalTextField.setText("");
        maxIntervalTextField.setText("");
    }

    @Override
    public void parse(UniformDistributionTimer obj) {
        minIntervalTextField.setText(Double.toString(obj.getMin()));
        maxIntervalTextField.setText(Double.toString(obj.getMax()));
    }

    @Override
    public UniformDistributionTimer create() {
        try {
            final double min = Double.parseDouble(minIntervalTextField.getText());
            final double range = Double.parseDouble(maxIntervalTextField.getText()) - min;
            return new UniformDistributionTimer(min, range);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        minIntervalLabel = new javax.swing.JLabel();
        minIntervalTextField = new javax.swing.JTextField();
        maxIntervalLabel = new javax.swing.JLabel();
        maxIntervalTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridLayout(2, 2));

        minIntervalLabel.setText("Min");
        add(minIntervalLabel);

        minIntervalTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minIntervalTextFieldActionPerformed(evt);
            }
        });
        add(minIntervalTextField);

        maxIntervalLabel.setText("Max");
        add(maxIntervalLabel);

        maxIntervalTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxIntervalTextFieldActionPerformed(evt);
            }
        });
        add(maxIntervalTextField);
    }// </editor-fold>//GEN-END:initComponents

    private void minIntervalTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minIntervalTextFieldActionPerformed
        try {
            Double.parseDouble(minIntervalTextField.getText());
        } catch (NumberFormatException e) {
            minIntervalTextField.setText("1.0");
        }
    }//GEN-LAST:event_minIntervalTextFieldActionPerformed

    private void maxIntervalTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxIntervalTextFieldActionPerformed
        try {
            Double.parseDouble(maxIntervalTextField.getText());
        } catch (NumberFormatException e) {
            maxIntervalTextField.setText("1.0");
        }
    }//GEN-LAST:event_maxIntervalTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel maxIntervalLabel;
    private javax.swing.JTextField maxIntervalTextField;
    private javax.swing.JLabel minIntervalLabel;
    private javax.swing.JTextField minIntervalTextField;
    // End of variables declaration//GEN-END:variables
}
