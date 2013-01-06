package com.gman.modeler.timer;

import com.gman.modeler.api.TimeUtils;
import com.gman.modeler.api.Timer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author gman
 * @since 9/8/12 8:42 PM
 */
public class TimeTableTimer implements Timer {

    private static final long serialVersionUID = 1L;

    private final List<Double> events = new ArrayList<>();

    public TimeTableTimer(List<Double> events) {
        this.events.addAll(events);
    }

    public List<Double> getEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public double nextTime(double currentTime) {
        for (final Double event : events) {
            if (event > currentTime) {
                return event;
            }
        }
        return Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeTable(");
        if (!events.isEmpty()) {
            sb.append(TimeUtils.timeString(events.get(0)));
        }
        for (int i=1; i<events.size(); i++) {
            sb.append(',').append(TimeUtils.timeString(events.get(i)));
        }
        sb.append(")");
        return sb.toString();
    }

    public static TimeTableTimer parseMoments(InputStream stream) throws IOException {
        String line;
        final List<Double> events = new ArrayList<>();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        while((line = reader.readLine()) != null) {
            events.add(Double.parseDouble(line));
        }

        return new TimeTableTimer(events);
    }

    public static TimeTableTimer parseIntervals(InputStream stream) throws IOException {
        double currentMoment = 0.0;
        String line;
        final List<Double> events = new ArrayList<>();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        while((line = reader.readLine()) != null) {
            currentMoment += Double.parseDouble(line);
            events.add(currentMoment);
        }

        return new TimeTableTimer(events);
    }
}
