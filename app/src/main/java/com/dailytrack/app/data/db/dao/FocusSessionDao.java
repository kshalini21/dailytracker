package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.FocusSessionEntity;
import java.util.List;

@Dao
public interface FocusSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FocusSessionEntity session);

    @Query("SELECT * FROM focus_sessions ORDER BY completed_at DESC")
    LiveData<List<FocusSessionEntity>> getAllSessions();

    @Query("SELECT SUM(duration_minutes) FROM focus_sessions")
    LiveData<Integer> getTotalFocusMinutes();

    @Query("SELECT COUNT(*) FROM focus_sessions")
    LiveData<Integer> getTotalSessionCount();

    @Query("DELETE FROM focus_sessions")
    void deleteAll();
}
