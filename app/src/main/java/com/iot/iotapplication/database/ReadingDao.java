package com.iot.iotapplication.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.iot.iotapplication.Reading;

import java.util.List;

@Dao
public interface ReadingDao {

    @Insert(onConflict = REPLACE)
    void insertReading(Reading reading);

    @Query("DELETE FROM readingDb")
    void clearReadingDb();

    @Query("SELECT * FROM readingDb")
    List<Reading> loadAllReadings();
}
