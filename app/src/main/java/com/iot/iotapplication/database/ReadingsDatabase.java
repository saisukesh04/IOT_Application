package com.iot.iotapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iot.iotapplication.Reading;

@Database(entities = {Reading.class}, version = 1, exportSchema = false)
public abstract class ReadingsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "readingDb";
    private static ReadingsDatabase sInstance;

    public static ReadingsDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    ReadingsDatabase.class, ReadingsDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public abstract ReadingDao readingDao();
}