package com.moqi.scheduleminiprogrambackend.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {

    public static Date getNextDay(Date date){
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        date=new Date(calendar.getTimeInMillis());
        return date;
    }

}
