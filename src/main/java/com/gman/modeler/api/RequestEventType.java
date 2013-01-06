package com.gman.modeler.api;

/**
 * Represents different types of events of Request life cycle
 * Lifecycle:
 * <pre>
 * {@link RequestEventType#CREATED}
 * [
 * {@link RequestEventType#ADDED_TO_THE_QUERY}
 * {@link RequestEventType#REMOVED_FROM_THE_QUERY}
 * {@link RequestEventType#STARTED_PROCESSING}
 * {
 * {@link RequestEventType#CHANGED_TYPE}
 * }
 * {@link RequestEventType#FINISHED_PROCESSING}
 * ]
 * {@link RequestEventType#RELEASED} | {@link RequestEventType#ABORTED}
 * </pre>
 * Sometimes events can occur in same time (like {@link RequestEventType#FINISHED_PROCESSING}
 * and {@link RequestEventType#RELEASED})
 *
 * @author gman
 */
public enum RequestEventType {
    /**
     * Just created
     */
    CREATED,
    /**
     * Has been added to the query
     */
    ADDED_TO_THE_QUERY,
    /**
     * Processing has started
     */
    STARTED_PROCESSING,
    /**
     * Processing has finished
     */
    FINISHED_PROCESSING,
    /**
     * Has been removed from the query
     */
    REMOVED_FROM_THE_QUERY,
    /**
     * Indicates that type has changed
     */
    CHANGED_TYPE,
    /**
     * Indicates that all processing has finished successfully
     */
    RELEASED,
    /**
     * Indicates that processing has finished unsuccessfully
     */
    ABORTED
}
