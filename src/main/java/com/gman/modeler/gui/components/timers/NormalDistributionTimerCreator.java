package com.gman.modeler.gui.components.timers;

import com.gman.modeler.gui.api.DataCreator;
import com.gman.modeler.timer.NormalDistributionTimer;

/**
 * @author gman
 */
public class NormalDistributionTimerCreator extends DataCreator<NormalDistributionTimer> {

    /**
     * Creates new form NormalDistributionTimerCreator
     */
    public NormalDistributionTimerCreator() {
        initComponents();
    }

    @Override
    public void reset() {
        meanTextField.setText("");
        sigmaTextField.setText("");
    }

    @Override
    public void parse(NormalDistributionTimer obj) {
        meanTextField.setText(Double.toString(obj.getMean()));
        sigmaTextField.setText(Double.toString(obj.getSigma()));
    }

    @Override
    public NormalDistributionTimer create() {
        try {
            final double mean = Double.parseDouble(meanTextField.getText());
            final double sigma = Double.parseDouble(sigmaTextField.getText());
            return new NormalDistributionTimer(mean, sigma);
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

        meanLabel = new javax.swing.JLabel();
        meanTextField = new javax.swing.JTextField();
        sigmaLabel = new javax.swing.JLabel();
        sigmaTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridLayout(2, 2));

        meanLabel.setText("mean");
        add(meanLabel);
        add(meanTextField);

        sigmaLabel.setText("sigma");
        add(sigmaLabel);
        add(sigmaTextField);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel meanLabel;
    private javax.swing.JTextField meanTextField;
    private javax.swing.JLabel sigmaLabel;
    private javax.swing.JTextField sigmaTextField;
    // End of variables declaration//GEN-END:variables
}