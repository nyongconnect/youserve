package com.devthrust.youserve.pojo;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EventDao {

    @Query("select * from event order by priority")
    LiveData<List<Events>> loadAllEvents();

    @Insert
    void insertEvent(Events events);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEvent(Events events);
    @Delete
    void deleteEvent(Events events);
    @Query("select * from event where mId = :id")
    LiveData<Events> loadEvent(int id);
}
