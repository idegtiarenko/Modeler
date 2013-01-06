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
 * @since 20.04.12 17:59
 */
public class RequestTimeDistributionStatisticsAggregator implements StatisticAggregator {

    private static final long serialVersionUID = 1L;

    private final List<Request> processed = new ArrayList<>();
    private boolean calculated = false;
    private double inQueue = 0.0;
    private double inService = 0.0;

    @Override
    public String getStatisticsName() {
        return "Request time distribution";
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
        return 1;
    }

    @Override
    public double getRecord(int domain, int record) {
        if (!calculated) {
            calculate();
        }
        switch (domain) {
            case 0:
                return inQueue;
            default:
                return inService;
        }
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        this.processed.clear();
        this.processed.addAll(processed);
    }

    @Override
    public void clear() {
        calculated = false;
    }

    private void calculate() {
        double total = 0.0;
        inQueue = 0.0;
        inService = 0.0;
        for (final Request request : processed) {
            final double queue = request.getFullTimeForEvent(RequestEventType.ADDED_TO_THE_QUERY);
            final double service = request.getFullTimeForEvent(RequestEventType.STARTED_PROCESSING);
            inQueue += queue;
            inService += service;
            total += queue + service;
        }
        if (total != 0.0) {
            inQueue /= total;
            inService /= total;
        }
    }
}
