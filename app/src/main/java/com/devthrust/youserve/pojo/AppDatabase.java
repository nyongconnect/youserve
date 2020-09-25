package com.devthrust.youserve.pojo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Events.class},version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    public static AppDatabase sInstance;
    public static final String DATABASE_NAME = "todolist";

    public static AppDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
            }
        return sInstance;

    }
    public abstract EventDao mEventDao();
}
