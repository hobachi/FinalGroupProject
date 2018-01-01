package com.finalgroupproject.Objects;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 */

public class Thermostat extends SugarRecord {

    @Unique
    private long id;
    private long created;

    private int day;
    private int hour;
    private int minute;

    private int date;
    private int month;
    private int year;

    private double temp;

    public Thermostat(){

    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;

        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(created);

        date = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }
}