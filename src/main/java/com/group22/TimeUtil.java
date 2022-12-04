package com.group22;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public TimeUtil(){

    }

    public String getTimeAgo(LocalDateTime pastTime) {
        Period dateBetween = Period.between(pastTime.toLocalDate(), LocalDateTime.now().toLocalDate());
        Duration timeBetween = Duration.between(pastTime.toLocalTime(), LocalDateTime.now());

        if (dateBetween.getYears() != 0) {
            return String.valueOf(dateBetween.getYears())+ 
                    (dateBetween.getYears() == 1 ? " year" : " years") + " ago";
        } else if (dateBetween.getMonths() != 0) {
            return String.valueOf(dateBetween.getMonths())+ 
                    (dateBetween.getMonths() == 1 ? " month" : " months") + " ago";
        } else if (dateBetween.getDays() != 0) {
            return String.valueOf(dateBetween.getDays())+ 
                    (dateBetween.getDays() == 1 ? " day" : " days") + " ago";
        } else if (timeBetween.toHours() != 0) {
            return String.valueOf(timeBetween.toHours())+ 
                    (timeBetween.toHours() == 1 ? " hour" : " hours") + " ago";
        } else if (timeBetween.toMinutes() != 0) {
            return String.valueOf(timeBetween.toMinutes())+ 
                    (timeBetween.toMinutes() == 1 ? " minute" : " minutes") + " ago";
        } else {
            return "A few moments ago";
        }
    }

    public String getStringifiedTime(int seconds){
        if (seconds >= 60){
            long minute = TimeUnit.SECONDS.toMinutes(seconds);
            int secMinusMin = (int) (seconds - (60*minute));
            return minute + (minute == 1 ? " minute" : "  minutes") + secMinusMin + (secMinusMin == 1 ? " second" : " seconds");  
        } else {
            return seconds + (seconds == 1 ? " second" : " seconds");
        }
    }

    public String getLevelTimeLeft(double seconds){
        final DecimalFormat df = new DecimalFormat("0.00");
        if (seconds >= 60){
            long minute = TimeUnit.SECONDS.toMinutes((long) seconds);
            int secMinusMin = (int) (seconds - (60*minute));
            return minute + "m " + secMinusMin + "s";  
        } else {
            return df.format(seconds) + "s";
        }
    }
}
