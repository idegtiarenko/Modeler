package com.gman.modeler.statistics;

import java.util.ArrayList;
import java.util.List;

import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Request;
import com.gman.modeler.api.Service;
import com.gman.modeler.api.StatisticAggregator;

/**
 * @author gman
 * @since 09.05.12 8:20
 */
public class ServiceLoadFactorDynamicStatisticsAggregator implements StatisticAggregator {

    private static final long serialVersionUID = 1L;

    private final List<double[]> loadFactors = new ArrayList<>();
    private int records = 0;
    private final List<String> names = new ArrayList<>();
    private int domains = 0;

    @Override
    public String getStatisticsName() {
        return "Service load factor dynamic";
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
        return records;
    }

    @Override
    public double getRecord(int domain, int record) {
        return loadFactors.get(record)[domain];
    }

    @Override
    public void init(List<Queue> queues, List<Service> services) {
        clear();
        domains = services.size() + 1;
        domains = services.size() + 1;
        for (int i = 0; i < domains - 1; i++) {
            names.add(services.get(i).getName());
        }
        names.add("Total");
    }

    @Override
    public void addRecord(List<Queue> queues, List<Service> services, List<Request> generated, List<Request> processed, List<Request> aborted) {
        double[] loads = new double[domains];
        if (records > 0) {
            double total = 0;
            for (int i = 0; i < domains - 1; i++) {
                double lf = loadFactors.get(records - 1)[i];
                lf = (lf * records + (services.get(i).hasProcessing() ? 1 : 0)) / (records + 1);
                loads[i] = lf;

                total = (total * i + lf) / (i + 1);
            }
            loads[domains - 1] = total;
        }
        loadFactors.add(loads);
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
