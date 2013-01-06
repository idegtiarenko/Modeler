package com.gman.modeler.componentsaggregators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Registers components of type T
 * By default components is registered by their className
 *
 * @author gman
 */
public abstract class AbstractComponentAggregator<T> {

    private final Map<String, Class<? extends T>> components = new HashMap<String, Class<? extends T>>();

    /**
     * Register component using its class name as name
     *
     * @param clazz is the class of the component
     */
    public final void registerComponent(final Class<? extends T> clazz) {
        components.put(clazz.getName(), clazz);
    }

    /**
     * Unregister component by its class (if it was registered using class name as name)
     *
     * @param clazz to unregister
     */
    public final void unregisterComponent(final Class<? extends T> clazz) {
        components.remove(clazz.getName());
    }

    /**
     * Obtains set of all registered components
     *
     * @return set of components
     */
    public final Set<String> getRegisteredComponents() {
        return components.keySet();
    }

    /**
     * Construct component by its name using default constructor
     *
     * @param name is the name of the component to obtain
     * @return instance of the component
     * @throws NoSuchComponentException is there is no such component or it has no default constructor
     */
    public final T getComponent(final String name) {
        try {
            return components.get(name).newInstance();
        } catch (final Exception e) {
            throw new NoSuchComponentException(e);
        }
    }

    /**
     * Construct component by its name using constructor with arguments
     *
     * @param name is the name of the component to obtain
     * @param args of the constructor
     * @return instance of the component
     * @throws NoSuchComponentException is there is no such component or it has no such constructor
     */
    public final T getComponent(final String name, final Object... args) {
        try {
            return (T) components.get(name).getConstructors()[0].newInstance(args);
        } catch (final Exception e) {
            throw new NoSuchComponentException(e);
        }
//        clazz.getConstructors()[0].newInstance()
//        This will help to init not default instances
//        throw new UnsupportedOperationException();
    }
}