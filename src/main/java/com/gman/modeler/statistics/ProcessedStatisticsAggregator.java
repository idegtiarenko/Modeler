package com.gman.modeler.statistics;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;

/**
 * @author gman
 * @since 15.04.12 11:13
 */
public class ProcessedStatisticsAggregator implements StatisticAggregator {

    private static final long serialVersionUID = 1L;

    private final List<Integer> generated = new ArrayList<>();
    private final List<Integer> processed = new ArrayList<>();
    private final List<Integer> aborted = new ArrayList<>();

    @Override
    public String getStatisticsName() {
        return "Processed";
    }

    @Override
    public int getDomainNumber() {
        return 4;
    }

    @Override
    public String getDomain(int index) {
        switch (index) {
            case 0:
                return "Generated";
            case 1:
                return "Processed";
            case 2:
                return "Aborted";
            default:
                return "In process";
        }
    }

    @Override
    public int getRecordsNumber() {
        return processed.size();
    }

    @Override
    public double getRecord(int domain, int record) {
        switch (domain) {
            case 0:
                return generated.get(record);
            case 1:
                return processed.get(record);
            case 2:
                return aborted.get(record);
            default:
                return generated.get(record) - processed.get(record) - aborted.get(record);
        }
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        this.generated.add(generated.size());
        this.processed.add(processed.size());
        this.aborted.add(aborted.size());
    }

    @Override
    public void clear() {
        generated.clear();
        processed.clear();
        aborted.clear();
    }
}
