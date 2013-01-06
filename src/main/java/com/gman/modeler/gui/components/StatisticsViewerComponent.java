/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gman.modeler.gui.components;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gman.modeler.api.Modeler;
import com.gman.modeler.api.StatisticAggregator;
import com.gman.modeler.gui.api.DataListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.AbstractXYDataset;

/**
 * @author gman
 */
public class StatisticsViewerComponent extends DataListener {

    private final List<StatisticAggregator> aggregators = new ArrayList<>();

    private double statisticsInterval = Double.NaN;

    private final DefaultListModel<String> domainsModel = new DefaultListModel<>();
    private final TableModel tableModel = new TableModel();
    private final ChartModel chartModel = new ChartModel();

    /**
     * Creates new form StatisticsViewerComponent
     */
    public StatisticsViewerComponent() {
        chartPanel = new ChartPanel(
                new JFreeChart(
                        new XYPlot(chartModel,
                                new NumberAxis(),
                                new NumberAxis(),
                                new StandardXYItemRenderer())
                )
        );
        spinnerModel = new SpinnerListModel();
        initComponents();


    }

    @Override
    public void setModeler(Modeler modeler) {
        aggregators.clear();
        aggregators.addAll(modeler.getStatistics());
        statisticsInterval = modeler.getStatisticsInterval();
        updateStatisticSelector();
        updateData(-1, null);
        updateDomainsSelector(-1);
    }

    @Override
    public String getComponentName() {
        return "Statistics viewer";
    }

    private void updateStatisticSelector() {
        final List<String> labels = new ArrayList<>();
        labels.add("");
        for (final StatisticAggregator aggregator : aggregators) {
            labels.add(aggregator.getStatisticsName());
        }
        spinnerModel.setList(labels);
        spinnerModel.setValue("");
        if (viewToggleButton.isSelected()) {
            dataPanel.removeAll();
            dataPanel.add(dataScrollPane, java.awt.BorderLayout.CENTER);
            viewToggleButton.setSelected(false);
        }
    }

    private void updateDomainsSelector(int statIndex) {
        domainsModel.clear();
        if (statIndex != -1) {
            final StatisticAggregator aggregator = aggregators.get(statIndex);
            for (int i = 0; i < aggregator.getDomainNumber(); i++) {
                domainsModel.addElement(aggregator.getDomain(i));
            }
            domainsList.setSelectionInterval(0, aggregator.getDomainNumber() - 1);
        }
    }

    private void updateData(int statIndex, int[] domains) {
        tableModel.update(statIndex, domains);
        chartModel.update(statIndex, domains);
    }

    private void updateData(int[] domains) {
        tableModel.update(domains);
        chartModel.update(domains);
    }

    private class TableModel extends AbstractTableModel {

        private int statIndex = -1;
        private int[] domains = null;

        public void update(int statIndex, int[] domains) {
            this.statIndex = statIndex;
            this.domains = domains != null ? Arrays.copyOf(domains, domains.length) : null;
            fireTableStructureChanged();
        }

        public void update(int[] domains) {
            this.domains = domains != null ? Arrays.copyOf(domains, domains.length) : null;
            fireTableStructureChanged();
        }

        @Override
        public int getRowCount() {
            return statIndex != -1 && domains != null ?
                    aggregators.get(statIndex).getRecordsNumber() : 0;
        }

        @Override
        public int getColumnCount() {
            return statIndex != -1 && domains != null ?
                    domains.length + 1 : 0;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "time";
                default:
                    return aggregators.get(statIndex).getDomain(domains[column - 1]);
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return rowIndex * statisticsInterval;
                default:
                    return aggregators.get(statIndex).getRecord(domains[columnIndex - 1], rowIndex);
            }
        }
    }

    private class ChartModel extends AbstractXYDataset {

        private int statIndex = -1;
        private int[] domains = null;

        public void update(int statIndex, int[] domains) {
            this.statIndex = statIndex;
            this.domains = domains != null ? Arrays.copyOf(domains, domains.length) : null;
            fireDatasetChanged();
        }

        public void update(int[] domains) {
            this.domains = domains != null ? Arrays.copyOf(domains, domains.length) : null;
            fireDatasetChanged();
        }

        @Override
        public int getSeriesCount() {
            return statIndex != -1 && domains != null ?
                    domains.length : 0;
        }

        @Override
        public Comparable getSeriesKey(int series) {
            return aggregators.get(statIndex).getDomain(domains[series]);
        }

        @Override
        public int getItemCount(int series) {
            return statIndex != -1 && domains != null ?
                    aggregators.get(statIndex).getRecordsNumber() : 0;
        }

        @Override
        public Number getX(int series, int item) {
            return item * statisticsInterval;
        }

        @Override
        public Number getY(int series, int item) {
            return aggregators.get(statIndex).getRecord(domains[series], item);
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

        dataPanel = new javax.swing.JPanel();
        dataScrollPane = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        propertiesPanel = new javax.swing.JPanel();
        statisticSpinner = new javax.swing.JSpinner();
        statisticLabel = new javax.swing.JLabel();
        viewToggleButton = new javax.swing.JToggleButton();
        domainsScrollPane = new javax.swing.JScrollPane();
        domainsList = new javax.swing.JList();

        setLayout(new java.awt.BorderLayout());

        dataPanel.setLayout(new java.awt.BorderLayout());

        dataTable.setModel(tableModel);
        dataScrollPane.setViewportView(dataTable);

        dataPanel.add(dataScrollPane, java.awt.BorderLayout.CENTER);

        add(dataPanel, java.awt.BorderLayout.CENTER);

        propertiesPanel.setLayout(new java.awt.BorderLayout());

        statisticSpinner.setModel(spinnerModel);
        statisticSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                statisticSpinnerStateChanged(evt);
            }
        });
        propertiesPanel.add(statisticSpinner, java.awt.BorderLayout.CENTER);

        statisticLabel.setText("Statistic");
        propertiesPanel.add(statisticLabel, java.awt.BorderLayout.LINE_START);

        add(propertiesPanel, java.awt.BorderLayout.PAGE_START);

        viewToggleButton.setText("Table/Chart");
        viewToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewToggleButtonActionPerformed(evt);
            }
        });
        add(viewToggleButton, java.awt.BorderLayout.PAGE_END);

        domainsList.setModel(domainsModel);
        domainsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                domainsListValueChanged(evt);
            }
        });
        domainsScrollPane.setViewportView(domainsList);

        add(domainsScrollPane, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void statisticSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_statisticSpinnerStateChanged
        final String statName = (String) statisticSpinner.getValue();
        if (statName.isEmpty()) {
            tableModel.update(-1, null);
            chartModel.update(-1, null);
        } else {
            for (int i = 0; i < aggregators.size(); i++) {
                if (aggregators.get(i).getStatisticsName().equals(statName)) {
                    updateData(i, null);
                    updateDomainsSelector(i);
                    updateData(i, domainsList.getSelectedIndices());
                    break;
                }
            }
        }
    }//GEN-LAST:event_statisticSpinnerStateChanged

    private void viewToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewToggleButtonActionPerformed
        dataPanel.removeAll();
        if (viewToggleButton.isSelected()) {
            dataPanel.add(chartPanel, java.awt.BorderLayout.CENTER);
        } else {
            dataPanel.add(dataScrollPane, java.awt.BorderLayout.CENTER);
        }
        updateUI();
    }//GEN-LAST:event_viewToggleButtonActionPerformed

    private void domainsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_domainsListValueChanged
        updateData(domainsList.getSelectedIndices());
    }//GEN-LAST:event_domainsListValueChanged

    private ChartPanel chartPanel;
    private SpinnerListModel spinnerModel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dataPanel;
    private javax.swing.JScrollPane dataScrollPane;
    private javax.swing.JTable dataTable;
    private javax.swing.JList domainsList;
    private javax.swing.JScrollPane domainsScrollPane;
    private javax.swing.JPanel propertiesPanel;
    private javax.swing.JLabel statisticLabel;
    private javax.swing.JSpinner statisticSpinner;
    private javax.swing.JToggleButton viewToggleButton;
    // End of variables declaration//GEN-END:variables
}
