package com.group22;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class RelativeTime {

    public RelativeTime(){

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
}
