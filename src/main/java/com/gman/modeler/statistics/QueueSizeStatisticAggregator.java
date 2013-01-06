package com.gman.modeler.statistics;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;

/**
 * @author gman
 * @since 15.04.12 11:03
 */
public class QueueSizeStatisticAggregator implements StatisticAggregator {

    private static final long serialVersionUID = 1L;

    private final List<int[]> sizes = new ArrayList<>();
    private final List<String> names = new ArrayList<>();
    private int domains = 0;

    @Override
    public String getStatisticsName() {
        return "Queue sizes";
    }

    @Override
    public int getDomainNumber() {
        return domains;
    }

    @Override
    public String getDomain(int index) {
        return names.get(index);
    }

    @Override
    public int getRecordsNumber() {
        return sizes.size();
    }

    @Override
    public double getRecord(int domain, int record) {
        return sizes.get(record)[domain];
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
        domains = queues.size();
        for (final Queue queue : queues) {
            names.add(queue.getName());
        }
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        final int[] newRecord = new int[domains];
        for (int i = 0; i < domains; i++) {
            newRecord[i] = queues.get(i).size();
        }
        sizes.add(newRecord);
    }

    @Override
    public void clear() {
        sizes.clear();
        names.clear();
        domains = 0;
    }
}
