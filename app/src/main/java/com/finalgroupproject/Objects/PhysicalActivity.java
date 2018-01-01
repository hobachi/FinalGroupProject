package com.finalgroupproject.Objects;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 */

public class PhysicalActivity extends SugarRecord {
    @Unique
    private long id;
    private long created;

    private int type;
    private int minutes;

    private int date;
    private int month;
    private int year;

    private String comments;

    public PhysicalActivity(){

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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