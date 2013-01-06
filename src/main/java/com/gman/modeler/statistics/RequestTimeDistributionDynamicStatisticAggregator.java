package com.gman.modeler.statistics;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.RequestEventType;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;

/**
 * @author gman
 * @since 09.05.12 8:21
 */
public class RequestTimeDistributionDynamicStatisticAggregator implements StatisticAggregator {

    private static final long serialVersionUID = 1L;

    private int lastCalculatedIndex = 0;
    private double totalTime = 0.0;
    private double inQueueTime = 0.0;
    private double inServiceTime = 0.0;
    private final List<Double> inQueue = new ArrayList<>();
    private final List<Double> inService = new ArrayList<>();

    @Override
    public String getStatisticsName() {
        return "Request time distribution dynamic";
    }

    @Override
    public int getDomainNumber() {
        return 2;
    }

    @Override
    public String getDomain(int index) {
        switch (index) {
            case 0:
                return "In queue";
            default:
                return "In service";
        }
    }

    @Override
    public int getRecordsNumber() {
        return inQueue.size();
    }

    @Override
    public double getRecord(int domain, int record) {
        switch (domain) {
            case 0:
                return inQueue.get(record);
            default:
                return inService.get(record);
        }
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        for (int i = lastCalculatedIndex; i < processed.size(); i++) {
            final double queue = processed.get(i).getFullTimeForEvent(RequestEventType.ADDED_TO_THE_QUERY);
            final double service = processed.get(i).getFullTimeForEvent(RequestEventType.STARTED_PROCESSING);
            inQueueTime += queue;
            inServiceTime += service;
            totalTime += queue + service;
        }
        if (totalTime > 0.0) {
            inQueue.add(inQueueTime / totalTime);
            inService.add(inServiceTime / totalTime);
        }
        lastCalculatedIndex = processed.size();
    }

    @Override
    public void clear() {
        lastCalculatedIndex = 0;
        totalTime = 0.0;
        inQueueTime = 0.0;
        inServiceTime = 0.0;
        inQueue.clear();
        inService.clear();
    }
}
