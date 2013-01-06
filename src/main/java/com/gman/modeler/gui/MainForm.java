package com.gman.modeler.gui;

import javax.swing.*;
import javax.swing.JPopupMenu.Separator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.gman.modeler.api.Modeler;
import com.gman.modeler.api.Queue;
import com.gman.modeler.api.QueueSelector;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;
import com.gman.modeler.api.Timer;
import com.gman.modeler.gui.api.DataListener;
import com.gman.modeler.gui.api.DataMediator;
import com.gman.modeler.gui.components.EventsViewerComponent;
import com.gman.modeler.gui.components.ModelerControllerComponent;
import com.gman.modeler.gui.components.QSchemeEditorComponent;
import com.gman.modeler.gui.components.QueueViewerComponent;
import com.gman.modeler.gui.components.RequestTypeEditorComponent;
import com.gman.modeler.gui.components.RequestViewerComponent;
import com.gman.modeler.gui.components.ServiceViewerComponent;
import com.gman.modeler.gui.components.StatisticsEditorComponent;
import com.gman.modeler.gui.components.StatisticsViewerComponent;
import com.gman.modeler.gui.components.queues.QueueCreator;
import com.gman.modeler.gui.components.queueselectors.RandomQueueSelectorCreator;
import com.gman.modeler.gui.components.queueselectors.ShortestQueueSelectorCreator;
import com.gman.modeler.gui.components.services.BatchServiceCreator;
import com.gman.modeler.gui.components.services.SimpleServiceCreator;
import com.gman.modeler.gui.components.statistics.AbortionProbabilityStatisticCreator;
import com.gman.modeler.gui.components.statistics.ProcessedStatisticsCreator;
import com.gman.modeler.gui.components.statistics.QueueSizeStatisticsCreator;
import com.gman.modeler.gui.components.statistics.RequestTimeDistributionDynamicStatisticCreator;
import com.gman.modeler.gui.components.statistics.RequestTimeDistributionStatisticCreator;
import com.gman.modeler.gui.components.statistics.RequestTimeStatisticCreator;
import com.gman.modeler.gui.components.statistics.ServiceLoadFactorDynamicStatisticCreator;
import com.gman.modeler.gui.components.statistics.ServiceLoadFactorStatisticCreator;
import com.gman.modeler.gui.components.timers.*;
import com.gman.modeler.gui.components.types.RequestTypeCreator;

/**
 * @author gman
 */
public class MainForm extends JFrame {

    public MainForm() {
        fileChooser = new JFileChooser();
        dataMediator = new DataMediator();
        initMediator();
        dataMediator.updateModeler(new Modeler());

        initComponents();
        initComponentsFromMediator();
        setTitle("Modeler");
        setSize(900, 600);
    }

    protected DataMediator getDataMediator() {
        return dataMediator;
    }

    private void initMediator() {
        dataMediator.addDataCreator(RequestType.class, new RequestTypeCreator(dataMediator));

        dataMediator.addDataCreator(Timer.class, new ConstantTimerCreator());
        dataMediator.addDataCreator(Timer.class, new UniformDistributionTimerCreator());
        dataMediator.addDataCreator(Timer.class, new NormalDistributionTimerCreator());
        dataMediator.addDataCreator(Timer.class, new ExponentialDistributionTimerCreator());
        dataMediator.addDataCreator(Timer.class, new NeverTimerCreator());
        dataMediator.addDataCreator(Timer.class, new TimeTableTimerCreator());

        dataMediator.addDataCreator(QueueSelector.class, new ShortestQueueSelectorCreator());
        dataMediator.addDataCreator(QueueSelector.class, new RandomQueueSelectorCreator());

        dataMediator.addDataCreator(Queue.class, new QueueCreator(dataMediator));

        dataMediator.addDataCreator(Service.class, new SimpleServiceCreator(dataMediator));
        dataMediator.addDataCreator(Service.class, new BatchServiceCreator(dataMediator));

        dataMediator.addDataCreator(StatisticAggregator.class, new ProcessedStatisticsCreator());
        dataMediator.addDataCreator(StatisticAggregator.class, new QueueSizeStatisticsCreator());
        dataMediator.addDataCreator(StatisticAggregator.class, new ServiceLoadFactorStatisticCreator());
        dataMediator.addDataCreator(StatisticAggregator.class, new ServiceLoadFactorDynamicStatisticCreator());
        dataMediator.addDataCreator(StatisticAggregator.class, new RequestTimeDistributionStatisticCreator());
        dataMediator.addDataCreator(StatisticAggregator.class, new RequestTimeDistributionDynamicStatisticCreator());
        dataMediator.addDataCreator(StatisticAggregator.class, new AbortionProbabilityStatisticCreator());
        dataMediator.addDataCreator(StatisticAggregator.class, new RequestTimeStatisticCreator());

        dataMediator.addDataListener(new ModelerControllerComponent());
        dataMediator.addDataListener(new RequestTypeEditorComponent());
        dataMediator.addDataListener(new QSchemeEditorComponent());
        dataMediator.addDataListener(new ServiceViewerComponent());
        dataMediator.addDataListener(new QueueViewerComponent());
        dataMediator.addDataListener(new RequestViewerComponent());
        dataMediator.addDataListener(new EventsViewerComponent());
        dataMediator.addDataListener(new StatisticsEditorComponent());
        dataMediator.addDataListener(new StatisticsViewerComponent());
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

        tabbedPane = new JTabbedPane();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        newMenuItem = new JMenuItem();
        openMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();
        separatorMenuItem = new Separator();
        exitMenuItem = new JMenuItem();
        aboutMenu = new JMenu();
        aboutMenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        fileMenu.setText("File");

        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newMenuItem.setText("New");
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuItem);

        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);
        fileMenu.add(separatorMenuItem);

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        aboutMenu.setText("About");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        aboutMenu.add(aboutMenuItem);

        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileChooser.getSelectedFile())));
                dataMediator.updateModeler((Modeler) in.readObject());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to open file", "error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Wrong file format", "error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {

                }
            }
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void saveMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileChooser.getSelectedFile())));
                out.writeObject(dataMediator.getModeler());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to write file", "error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {

                }
            }
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void exitMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void newMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        dataMediator.updateModeler(new Modeler());
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Creator: Degtiarenko E. G.\nDNU-PZ-08-1\nTutor: Macuga O. N.", "about", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    protected final void initComponentsFromMediator() {
        tabbedPane.removeAll();
        for (DataListener component : dataMediator) {
            tabbedPane.addTab(component.getComponentName(), component);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    private final DataMediator dataMediator;
    private final JFileChooser fileChooser;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenu aboutMenu;
    private JMenuItem aboutMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JMenuBar menuBar;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private Separator separatorMenuItem;
    private JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
