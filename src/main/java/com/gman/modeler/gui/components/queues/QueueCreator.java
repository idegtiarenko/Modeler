/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gman.modeler.gui.components.queues;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.RequestType;
import com.gman.modeler.gui.api.DataCreator;
import com.gman.modeler.gui.api.DataMediator;
import com.gman.modeler.gui.components.types.RequestTypeComboBoxModel;
import com.gman.modeler.queues.LimitedQueueProxy;
import com.gman.modeler.queues.SimpleInvertedQueue;
import com.gman.modeler.queues.SimpleQueue;
import com.gman.modeler.queues.StatisticsQueueProxy;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author gman
 */
public class QueueCreator extends DataCreator<Queue> {

    private static final PairedList<String, Class> QUEUE_TYPES = new PairedList<>(
            new Pair<String, Class>("FIFO", SimpleQueue.class),
            new Pair<String, Class>("LIFO", SimpleInvertedQueue.class));

    private static final PairedList<String, Boolean> OPTIONS = new PairedList<>(
            new Pair<>("NO", Boolean.FALSE),
            new Pair<>("YES", Boolean.TRUE));

    private static final int UNLIMITED = 0;

    private final DataMediator mediator;
    private final RequestTypeComboBoxModel model;

    /**
     * Creates new form QueueCreator
     */
    public QueueCreator(DataMediator mediator) {
        this.mediator = mediator;
        this.model = new RequestTypeComboBoxModel(mediator);
        initComponents();
    }

    @Override
    public void reset() {
        typeComboBox.setSelectedIndex(QUEUE_TYPES.findIndexByValue(SimpleQueue.class));
        limitSpinner.setValue(Integer.valueOf(UNLIMITED));
        statisticsComboBox.setSelectedIndex(OPTIONS.findIndexByValue(Boolean.FALSE));
        acceptedTypesList.clearSelection();
    }

    @Override
    public void parse(Queue obj) {
        if (obj instanceof StatisticsQueueProxy) {
            statisticsComboBox.setSelectedIndex(OPTIONS.findIndexByValue(Boolean.TRUE));
            obj = ((StatisticsQueueProxy) obj).getUnderlying();
        } else {
            statisticsComboBox.setSelectedIndex(OPTIONS.findIndexByValue(Boolean.FALSE));
        }
        if (obj instanceof LimitedQueueProxy) {
            limitSpinner.setValue(((LimitedQueueProxy) obj).getLimit());
            obj = ((LimitedQueueProxy) obj).getUnderlying();
        } else {
            limitSpinner.setValue(UNLIMITED);
        }
        typeComboBox.setSelectedIndex(QUEUE_TYPES.findIndexByValue(obj.getClass()));

        final Set<RequestType> acceptedTypes = ((com.gman.modeler.queues.AbstractQueue) obj).getAccept();
        final Set<Integer> indicesToSelect = new TreeSet<>();
        int index = 0;
        for (final RequestType type : mediator.getModeler().getTypes()) {
            if (acceptedTypes.contains(type)) {
                indicesToSelect.add(index);
            }
            index++;
        }
        final int[] selectedIndices = new int[indicesToSelect.size()];
        index = 0;
        for (final Integer indexToSelect : indicesToSelect) {
            selectedIndices[index] = indexToSelect;
            index++;
        }
        acceptedTypesList.setSelectedIndices(selectedIndices);
    }

    @Override
    public Queue create() {
        final Set<RequestType> acceptedTypes = new HashSet<>();
        for (int index : acceptedTypesList.getSelectedIndices()) {
            acceptedTypes.add(mediator.getModeler().getTypes().get(index));
        }
        Class queueType = QUEUE_TYPES.findValueByIndex(typeComboBox.getSelectedIndex());
        Queue queue = null;
        try {
            queue = (Queue) queueType.getConstructor(Set.class).
                    newInstance(new HashSet<>(mediator.getModeler().getTypes()));
        } catch (final Exception ex) {
            //this should never occur
        }
        int limit = (Integer) limitSpinner.getValue();
        if (limit != UNLIMITED) {
            queue = new LimitedQueueProxy(queue, limit);
        }
        if (OPTIONS.findValueByIndex(statisticsComboBox.getSelectedIndex()).equals(Boolean.TRUE)) {
            queue = new StatisticsQueueProxy(queue);
        }
        return queue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        limitPanel = new JPanel();
        typesPanel = new JPanel();
        acceptedTypesLabel = new JLabel();
        acceptedTypesScrollPane = new JScrollPane();
        acceptedTypesList = new JList();
        propertiesPanel = new JPanel();
        typeLabel = new JLabel();
        typeComboBox = new JComboBox();
        limitLabel = new JLabel();
        limitSpinner = new JSpinner();
        statisticsLabel = new JLabel();
        statisticsComboBox = new JComboBox();

        limitPanel.setLayout(new GridLayout(1, 2));

        setLayout(new BorderLayout());

        typesPanel.setLayout(new GridLayout(1, 2));

        acceptedTypesLabel.setText("Accepted types");
        typesPanel.add(acceptedTypesLabel);

        acceptedTypesList.setModel(model);
        acceptedTypesScrollPane.setViewportView(acceptedTypesList);

        typesPanel.add(acceptedTypesScrollPane);

        add(typesPanel, BorderLayout.CENTER);

        propertiesPanel.setLayout(new GridLayout(3, 2));

        typeLabel.setText("Type");
        propertiesPanel.add(typeLabel);

        typeComboBox.setModel(new DefaultComboBoxModel(QUEUE_TYPES.getKeys()));
        propertiesPanel.add(typeComboBox);

        limitLabel.setText("Limit (0 means no limit)");
        propertiesPanel.add(limitLabel);

        limitSpinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        propertiesPanel.add(limitSpinner);

        statisticsLabel.setText("Gather statistics");
        propertiesPanel.add(statisticsLabel);

        statisticsComboBox.setModel(new DefaultComboBoxModel(OPTIONS.getKeys()));
        propertiesPanel.add(statisticsComboBox);

        add(propertiesPanel, BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private static final class PairedList<K, V> extends ArrayList<Pair<K, V>> {

        public PairedList(Pair<K, V>... pairs) {
            addAll(Arrays.asList(pairs));
        }

        public int findIndexByValue(V value) {
            for (int i = 0; i < size(); i++) {
                if (get(i).getValue().equals(value)) {
                    return i;
                }
            }
            return -1;
        }

        public V findValueByIndex(int index) {
            return get(index).getValue();
        }

        public String[] getKeys() {
            final String[] keys = new String[size()];
            int index = 0;
            for (final Pair<K, V> pair : this) {
                keys[index] = pair.getKey().toString();
                index++;
            }
            return keys;
        }
    }

    private static final class Pair<K, V> {

        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel acceptedTypesLabel;
    private JList acceptedTypesList;
    private JScrollPane acceptedTypesScrollPane;
    private JLabel limitLabel;
    private JPanel limitPanel;
    private JSpinner limitSpinner;
    private JPanel propertiesPanel;
    private JComboBox statisticsComboBox;
    private JLabel statisticsLabel;
    private JComboBox typeComboBox;
    private JLabel typeLabel;
    private JPanel typesPanel;
    // End of variables declaration//GEN-END:variables
}