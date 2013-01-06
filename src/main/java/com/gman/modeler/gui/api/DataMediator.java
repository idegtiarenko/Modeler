package com.gman.modeler.gui.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gman.modeler.api.Modeler;

/**
 * @author gman
 */
public class DataMediator implements Iterable<DataListener> {

    private final List<DataListener> listeners = new ArrayList<>();
    private final Map<Class, List<DataCreator>> creators = new HashMap<>();
    private Modeler dataHolder;

    public DataMediator() {
    }

    public void addDataListener(DataListener listener) {
        this.listeners.add(listener);
        if (listener instanceof DataModifier) {
            ((DataModifier) listener).initCallBack(this);
        }
    }

    public void addDataListeners(List<DataListener> listeners) {
        for (DataListener listener : listeners) {
            addDataListener(listener);
        }
    }

    public <T> void addDataCreator(Class<T> clazz, DataCreator<? extends T> editor) {
        if (creators.containsKey(clazz)) {
            creators.get(clazz).add(editor);
        } else {
            final List<DataCreator> dataCreators = new ArrayList<>();
            dataCreators.add(editor);
            creators.put(clazz, dataCreators);
        }
    }

    public <T> void addDataCreators(Class<T> clazz, List<DataCreator<? extends T>> editors) {
        if (creators.containsKey(clazz)) {
            creators.get(clazz).addAll(editors);
        } else {
            final List<DataCreator> dataCreators = new ArrayList<>();
            dataCreators.addAll(editors);
            creators.put(clazz, dataCreators);
        }
    }

    public List<DataCreator> getCreatorsFor(Class clazz) {
        return creators.get(clazz);
    }

    public void updateModeler(Modeler modeler) {
        this.dataHolder = modeler;
        fireDataUpdated();
    }

    public Modeler getModeler() {
        return dataHolder;
    }

    public void fireDataUpdated() {
        for (DataListener listener : listeners) {
            listener.setModeler(dataHolder);
        }
    }

    @Override
    public Iterator<DataListener> iterator() {
        return listeners.iterator();
    }
}
