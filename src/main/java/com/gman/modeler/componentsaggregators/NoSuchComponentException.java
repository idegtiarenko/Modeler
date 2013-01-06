package com.gman.modeler.componentsaggregators;

/**
 * This exception indicates that service with specified name or class is not found
 *
 * @author gman
 */
public class NoSuchComponentException extends RuntimeException {

    /**
     * Constructs exception in case constructing service fails
     *
     * @param throwable is the cause of constructing fails
     */
    public NoSuchComponentException(final Exception throwable) {
        super(throwable);
    }
}