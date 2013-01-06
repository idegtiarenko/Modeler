package com.gman.modeler.statistics;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;

/**
 * @author gman
 * @since 15.04.12 11:04
 */
public class ServiceLoadFactorStatisticAggregator implements StatisticAggregator {

    private static final long serialVersionUID = 1L;

    private final List<Double> loadFactors = new ArrayList<>();
    private int records = 0;
    private final List<String> names = new ArrayList<>();
    private int domains = 0;


    @Override
    public String getStatisticsName() {
        return "Service load factor";
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
        return 1;
    }

    @Override
    public double getRecord(int domain, int record) {
        return loadFactors.get(domain);
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
        domains = services.size() + 1;
        for (int i = 0; i < domains - 1; i++) {
            names.add(services.get(i).getName());
            loadFactors.add(0.0);
        }
        names.add("Total");
        loadFactors.add(0.0);
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        double total = 0;
        for (int i = 0; i < domains - 1; i++) {
            double lf = loadFactors.get(i);
            lf = (lf * records + (services.get(i).hasProcessing() ? 1 : 0)) / (records + 1);
            loadFactors.set(i, lf);

            total = (total * i + lf) / (i + 1);
        }
        loadFactors.set(loadFactors.size() - 1, total);
        records++;
    }

    @Override
    public void clear() {
        loadFactors.clear();
        records = 0;
        names.clear();
        domains = 0;
    }
}
