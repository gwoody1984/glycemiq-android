package com.eveningoutpost.dexdrip.Glycemiq.Utils;

import java.util.Calendar;

/**
 * Created by woodb on 7/20/2017.
 */

public final class DateUtils {

    private DateUtils() {
    }

    public static Long getStartOfDay(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MILLISECOND, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.HOUR_OF_DAY, 0);

        return today.getTimeInMillis();
    }

    public static Long getEndOfDay(){
        return getStartOfDay() + (24 * 60 * 60 * 1000);
    }
}
