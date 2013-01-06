package com.gman.modeler.statistics;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;

/**
 * @author gman
 * @since 13.05.12 16:00
 */
public class RequestTimeStatisticAggregator implements StatisticAggregator {

    private List<Double> time = new ArrayList<>();

    private double sumTime = 0.0;
    private int lastIndex = 0;

    @Override
    public String getStatisticsName() {
        return "Average request time";
    }

    @Override
    public int getDomainNumber() {
        return 1;
    }

    @Override
    public String getDomain(int index) {
        return "Average request time";
    }

    @Override
    public int getRecordsNumber() {
        return time.size();
    }

    @Override
    public double getRecord(int domain, int record) {
        return time.get(record);
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        for (int i = lastIndex; i < processed.size(); i++) {
            sumTime += processed.get(i).getFullTime();
            lastIndex++;
        }
        if (lastIndex > 0) {
            time.add(sumTime / lastIndex);
        }
    }

    @Override
    public void clear() {
        time.clear();
        sumTime = 0.0;
        lastIndex = 0;
    }
}
