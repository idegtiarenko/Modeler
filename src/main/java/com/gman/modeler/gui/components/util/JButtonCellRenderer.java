package com.gman.modeler.gui.components.util;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author gman
 * @since 15.04.12 16:03
 */
public class JButtonCellRenderer extends JButton implements TableCellRenderer {

    public JButtonCellRenderer(String text) {
        super(text);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
