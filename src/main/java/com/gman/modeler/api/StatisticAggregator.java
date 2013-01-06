package com.gman.modeler.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface of the class that aggregates statistics
 *
 * @author gman
 * @since 15.04.12 10:40
 */
public interface StatisticAggregator extends Serializable {

    /**
     * Name of the aggregator
     *
     * @return the name
     */
    String getStatisticsName();

    /**
     * Number of the calculated statistics
     *
     * @return number of the calculated statistics
     */
    int getDomainNumber();

    /**
     * Name of the selected statistic
     *
     * @param index of the statistic in list
     * @return name of the statistic by index
     */
    String getDomain(int index);

    /**
     * Number of the records, taken during the modeling
     *
     * @return number of records
     */
    int getRecordsNumber();

    /**
     * Obtain statistics value from selected field and record
     *
     * @param domain is the statistics domain
     * @param record is the record number
     * @return the statistics value
     */
    double getRecord(int domain, int record);

    /**
     * Initialize aggregator {@link com.gman.modeler.api.StatisticAggregator#clear()} may be called
     *
     * @param queues   is the queues definitions
     * @param services is the service definitions
     */
    void init(List<Queue> queues, List<Service> services);

    /**
     * Takes statistics from the modeler
     *
     * @param queues    is the current queues state
     * @param services  is the current services state
     * @param generated is the generated requests
     * @param processed is the fully processed requests
     * @param aborted   is the aborted requests
     */
    void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted);

    /**
     * Reset internal state and clear all data
     */
    void clear();
}
