package com.gman.modeler.gui.components;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.gman.modeler.api.Modeler;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.gui.ComponentsFactory;
import com.gman.modeler.gui.api.DataModifier;
import com.gman.modeler.gui.components.util.JButtonCellRenderer;

/**
 * @author gman
 */
public class RequestTypeEditorComponent extends DataModifier {

    private static final int INDEX_WIDTH = 25;
    private static final int NAME_WIDTH = 100;
    private static final int INTERVAL_WIDTH = 200;
    private static final int CONTROL_WIDTH = 60;

    private Modeler modeler;
    private final ComponentsFactory factory;
    private final TypeTableModel tableModel;

    /**
     * Creates new form RequestTypeEditorComponent
     */
    public RequestTypeEditorComponent() {
        factory = new ComponentsFactory(null);
        tableModel = new TypeTableModel();

        initComponents();

        typesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        typesTable.getColumnModel().getColumn(0).setPreferredWidth(INDEX_WIDTH);
        typesTable.getColumnModel().getColumn(1).setPreferredWidth(NAME_WIDTH);
        typesTable.getColumnModel().getColumn(2).setPreferredWidth(INTERVAL_WIDTH);
        typesTable.getColumnModel().getColumn(3).setPreferredWidth(INTERVAL_WIDTH);
        typesTable.getColumnModel().getColumn(4).setPreferredWidth(INTERVAL_WIDTH);
        typesTable.getColumnModel().getColumn(5).setPreferredWidth(CONTROL_WIDTH);
        typesTable.getColumnModel().getColumn(6).setPreferredWidth(CONTROL_WIDTH);

        typesTable.getColumnModel().getColumn(5).setCellRenderer(new JButtonCellRenderer("edit"));
        typesTable.getColumnModel().getColumn(6).setCellRenderer(new JButtonCellRenderer("del"));

        typesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int column = typesTable.getColumnModel().getColumnIndexAtX(e.getX());
                final int row = e.getY() / typesTable.getRowHeight();
                switch (column) {
                    case 5: {
                        //open edit dialog
                        final RequestType oldType = modeler.getTypes().get(row);
                        final RequestType newType = factory.showDialogFor(RequestType.class, getMediator(), oldType);
                        if (newType != null) {
                            modeler.getTypes().set(row, newType);
                            getMediator().updateModeler(modeler);
                        }
                    }
                    break;
                    case 6: {
                        //remove
                        modeler.getTypes().remove(row);
                        getMediator().updateModeler(modeler);
                    }
                    break;
                }
            }
        });
    }

    @Override
    public String getComponentName() {
        return "Request types";
    }

    @Override
    public void setModeler(Modeler modeler) {
        this.modeler = modeler;
        tableModel.update();
    }

    private class TypeTableModel extends AbstractTableModel {

        private void update() {
            fireTableDataChanged();
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public int getRowCount() {
            return modeler != null ? modeler.getTypes().size() : 0;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "i";
                case 1:
                    return "name";
                case 2:
                    return "income interval";
                case 3:
                    return "processing interval";
                case 4:
                    return "abort interval";
                case 5:
                    return "edit";
                default:
                    return "del";
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final RequestType type = modeler.getTypes().get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return rowIndex;
                case 1:
                    return type.getName();
                case 2:
                    return type.getIncomeInterval();
                case 3:
                    return type.getProcessingInterval();
                case 4:
                    return type.getAbortInterval();
                default:
                    return "-";
            }
        }
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

        typesScrollPane = new javax.swing.JScrollPane();
        typesTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        typesTable.setModel(tableModel);
        typesScrollPane.setViewportView(typesTable);

        add(typesScrollPane, java.awt.BorderLayout.CENTER);

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        add(addButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        final RequestType type = factory.showDialogFor(RequestType.class, getMediator());
        if (type != null) {
            modeler.getTypes().add(type);
            getMediator().updateModeler(modeler);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JScrollPane typesScrollPane;
    private javax.swing.JTable typesTable;
    // End of variables declaration//GEN-END:variables
}
