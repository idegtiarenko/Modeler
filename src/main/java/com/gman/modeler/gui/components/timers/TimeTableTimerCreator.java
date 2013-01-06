/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gman.modeler.gui.components.timers;

import com.gman.modeler.gui.api.DataCreator;
import com.gman.modeler.gui.components.util.JButtonCellRenderer;
import com.gman.modeler.timer.TimeTableTimer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gman
 */
public class TimeTableTimerCreator extends DataCreator<TimeTableTimer> {

    private static final int TIME_WIDTH = 240;
    private static final int CONTROL_WIDTH = 60;
    
    /**
     * Creates new form TimeTableTimerCreator
     */
    public TimeTableTimerCreator() {
        fileChooser = new JFileChooser();
        model = new TimeTableModel();
        initComponents();

        timeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        timeTable.getColumnModel().getColumn(0).setPreferredWidth(TIME_WIDTH);
        timeTable.getColumnModel().getColumn(1).setPreferredWidth(CONTROL_WIDTH);
        timeTable.getColumnModel().getColumn(1).setCellRenderer(new JButtonCellRenderer("x"));
        timeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int column = timeTable.getColumnModel().getColumnIndexAtX(e.getX());
                final int row = e.getY() / timeTable.getRowHeight();
                switch (column) {
                    case 1: {
                        model.removeEvent(row);
                    }
                    break;
                }
            }
        });
    
    }

    @Override
    public void reset() {
        model.reset();
    }
    
    @Override
    public void parse(TimeTableTimer obj) {
        model.setEvents(obj.getEvents());
    }

    @Override
    public TimeTableTimer create() {
        return new TimeTableTimer(model.getEvents());
    }
    
    private final JFileChooser fileChooser;
    private final TimeTableModel model;

    private static class TimeTableModel extends AbstractTableModel {

        private final List<Double> events = new ArrayList<>();
        
        public void reset() {
            events.clear();
            fireTableDataChanged();
        }
        
        public void removeEvent(final int index) {
            if (index < events.size()) {
                events.remove(index);
                fireTableDataChanged();    
            }
        }
        
        public void setEvents(final List<Double> events) {
            this.events.clear();
            this.events.addAll(events);
            fireTableDataChanged();
        }
        
        public List<Double> getEvents() {
            return Collections.unmodifiableList(events);
        }
        
        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return events.size() + 1;
        }

        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0:
                    return "Time";
                default:
                    return "Remove";
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex < events.size()) {
                switch(columnIndex) {
                    case 0:
                        return events.get(rowIndex);
                    default:
                        return "x";
                }
            } else {
                return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 0;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            try {
                final Double val = Double.parseDouble(aValue.toString());
                if (rowIndex < events.size()) {
                    events.set(rowIndex, val);
                } else {
                    events.add(val);
                }
            } catch (NumberFormatException e) {
                
            }
            fireTableDataChanged();
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

        timeScrollPane = new javax.swing.JScrollPane();
        timeTable = new javax.swing.JTable();
        loadFromFileButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        timeTable.setModel(model);
        timeScrollPane.setViewportView(timeTable);

        add(timeScrollPane, java.awt.BorderLayout.CENTER);

        loadFromFileButton.setText("Load from file");
        loadFromFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFromFileButtonActionPerformed(evt);
            }
        });
        add(loadFromFileButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void loadFromFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFromFileButtonActionPerformed
        if (fileChooser.showOpenDialog(this) == JFileChooser.OPEN_DIALOG) {
            InputStream stream = null;
            try {
                stream = new BufferedInputStream(
                        new FileInputStream(fileChooser.getSelectedFile()));
                parse(TimeTableTimer.parseIntervals(stream));
            } catch (IOException ex) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ex1) {
                    }
                }
            }
            
        }
    }//GEN-LAST:event_loadFromFileButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton loadFromFileButton;
    private javax.swing.JScrollPane timeScrollPane;
    private javax.swing.JTable timeTable;
    // End of variables declaration//GEN-END:variables
}
