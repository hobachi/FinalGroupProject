package com.finalgroupproject.Objects;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 */

public class Nutrition extends SugarRecord {

    @Unique
    private long id;
    private long created;

    private int date;
    private int month;
    private int year;

    private String name;

    private double calories;
    private double totalFat;
    private double totalCarbs;

    public Nutrition(){

    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
