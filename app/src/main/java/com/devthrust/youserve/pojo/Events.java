package com.devthrust.youserve.pojo;


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "event")
public class Events {
    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "title")
    String mTitle;
    @ColumnInfo(name = "updated_at")
    Date mDate;
    @ColumnInfo(name = "priority")
    int mPriority;

    @Ignore
    public Events(String title, Date date, int priority){
        mTitle = title;
        mDate = date;
        mPriority = priority;
    }
    public Events(int id, String title, Date date, int priority){
        mId = id;
        mTitle = title;
        mDate = date;
        mPriority = priority;
        mId = id;
    }



    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
