package com.dailytrack.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    public static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
    public static final SimpleDateFormat DISPLAY_FULL = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());

    /** Returns [startOfDay, endOfDay) in milliseconds for today */
    public static long[] getTodayRange() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        long end = cal.getTimeInMillis();
        return new long[]{start, end};
    }

    /** Returns today's date string in yyyy-MM-dd format */
    public static String todayString() {
        return SDF.format(new Date());
    }

    /** Returns date string for given millis */
    public static String dateString(long millis) {
        return SDF.format(new Date(millis));
    }

    /** Returns display-friendly date string */
    public static String displayDate(long millis) {
        if (millis == 0) return "";
        long[] today = getTodayRange();
        long[] tomorrow = getTomorrowRange();
        if (millis >= today[0] && millis < today[1]) return "Today";
        if (millis >= tomorrow[0] && millis < tomorrow[1]) return "Tomorrow";
        return DISPLAY_FORMAT.format(new Date(millis));
    }

    /** Returns [startOfTomorrow, endOfTomorrow) in ms */
    public static long[] getTomorrowRange() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return new long[]{start, cal.getTimeInMillis()};
    }

    /** Returns current day of week: 1=Monday ... 7=Sunday */
    public static int todayDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        int dow = cal.get(Calendar.DAY_OF_WEEK); // 1=Sun ... 7=Sat
        // Convert to 1=Mon ... 7=Sun
        return (dow == Calendar.SUNDAY) ? 7 : dow - 1;
    }

    /** Returns friendly day name */
    public static String dayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: return "";
        }
    }

    /** Returns short day label */
    public static String shortDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1: return "Mon";
            case 2: return "Tue";
            case 3: return "Wed";
            case 4: return "Thu";
            case 5: return "Fri";
            case 6: return "Sat";
            case 7: return "Sun";
            default: return "";
        }
    }

    /** Returns days until given timestamp (0 = today) */
    public static long daysUntil(long millis) {
        long now = System.currentTimeMillis();
        return (millis - now) / (1000L * 60 * 60 * 24);
    }

    /** Get greeting based on time of day */
    public static String getGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 12) return "Good morning";
        else if (hour < 17) return "Good afternoon";
        else return "Good evening";
    }

    /** Format minutes into readable string e.g. "1h 25m" */
    public static String formatMinutes(int minutes) {
        if (minutes < 60) return minutes + " min";
        int hrs = minutes / 60;
        int mins = minutes % 60;
        if (mins == 0) return hrs + " hr" + (hrs > 1 ? "s" : "");
        return hrs + "h " + mins + "m";
    }

    /** Returns start of day millis for a given calendar date */
    public static long startOfDay(Calendar cal) {
        Calendar c = (Calendar) cal.clone();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
}
