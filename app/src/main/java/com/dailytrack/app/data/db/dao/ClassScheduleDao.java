package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.ClassScheduleEntity;
import java.util.List;

@Dao
public interface ClassScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ClassScheduleEntity schedule);

    @Update
    void update(ClassScheduleEntity schedule);

    @Delete
    void delete(ClassScheduleEntity schedule);

    @Query("SELECT * FROM class_schedules ORDER BY day_of_week ASC, start_time ASC")
    LiveData<List<ClassScheduleEntity>> getAllSchedules();

    @Query("SELECT * FROM class_schedules WHERE day_of_week = :day ORDER BY start_time ASC")
    LiveData<List<ClassScheduleEntity>> getSchedulesForDay(int day);

    @Query("SELECT COUNT(*) FROM class_schedules WHERE day_of_week = :day")
    LiveData<Integer> getClassCountForDay(int day);

    @Query("DELETE FROM class_schedules")
    void deleteAll();
}
