package com.gman.modeler.api;

/**
 * This class provide utils to convert time representations
 *
 * @author gman
 * @since 29.04.12 16:52
 */
public final class TimeUtils {

    private TimeUtils() {
    }

    /**
     * Convert time to its string representation
     *
     * @param time is the time to convert
     * @return string representation
     */
    public static String timeString(double time) {
        return timeString(timeToComponents(time));
    }

    /**
     * Convert time to its string representation
     *
     * @param components is the time to convert
     * @return string representation
     */
    public static String timeString(int[] components) {
        return String.format("%02d:%02d:%02d.%03d", components[HOUR], components[MINUTE], components[SECOND], components[MILLI]);
    }

    /**
     * Convert double time representation to ist components (hours:minutes:seconds:millis)
     *
     * @param time is the time to convert
     * @return converted representation
     */
    public static int[] timeToComponents(double time) {
        final int[] components = new int[MILLI + 1];
        final long ltime = Math.round(time);
        components[MILLI] = (int) Math.round(Math.abs(ltime - time) * 1000);
        components[SECOND] = (int) ltime % 60;
        components[MINUTE] = (int) ((ltime - components[SECOND]) % 3600) / 60;
        components[HOUR] = (int) ltime / 3600;
        return components;
    }

    /**
     * Convert time components (hours:minutes:seconds:millis) to its double representation
     *
     * @param components is the time to convert
     * @return converted representation
     */
    public static double timeFromComponents(int[] components) {
        return (components[HOUR] * 60 + components[MINUTE]) * 60 + components[SECOND] + components[MILLI] / 1000;
    }

    /**
     * Index if milliseconds field
     */
    public static final int MILLI = 3;
    /**
     * Index of seconds filed
     */
    public static final int SECOND = 2;
    /**
     * Index of minutes field
     */
    public static final int MINUTE = 1;
    /**
     * Index of hours field
     */
    public static final int HOUR = 0;

}
