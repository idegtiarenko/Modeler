package com.gman.modeler.statistics;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;

/**
 * @author gman
 * @since 09.05.12 8:28
 */
public class AbortionProbabilityStatisticsAggregator implements StatisticAggregator {

    private final List<Double> abortionProbability = new ArrayList<>();

    @Override
    public String getStatisticsName() {
        return "Abortion probability";
    }

    @Override
    public int getDomainNumber() {
        return 1;
    }

    @Override
    public String getDomain(int index) {
        return "Abortion probability";
    }

    @Override
    public int getRecordsNumber() {
        return abortionProbability.size();
    }

    @Override
    public double getRecord(int domain, int record) {
        return abortionProbability.get(record);
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        double total = processed.size() + aborted.size();
        abortionProbability.add(total > 0.0 ? aborted.size() / total : 0.0);
    }

    @Override
    public void clear() {
        abortionProbability.clear();
    }
}
