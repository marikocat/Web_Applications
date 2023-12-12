package com.my.util;

import com.my.domain.Role;
import com.my.domain.Tariff;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UtilFunctions {
    public static double findTotalCost(List<Tariff> cart) {
        double sum = cart.stream().mapToDouble(Tariff::getCost).sum();
        if (cart.size() > 1) {
            sum *= (1 - cart.size() / 10.0);
        }
        return sum;
    }

    public static Date getStartDate() {
        Calendar cal = Calendar.getInstance();
        Date startDate = new Date(cal.getTimeInMillis());
        System.out.println("startDate ==> " + startDate);
        return startDate;
    }

    public static Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = new Date(cal.getTimeInMillis());
        System.out.println("endDate ==> " + endDate);
        return endDate;
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance(Locale.US);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy EEEE", Locale.US);
        String result = formatter.format(cal.getTime());
        return result;
    }
}
