package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.HabitEntity;
import com.dailytrack.app.data.db.entity.HabitCompletionEntity;
import java.util.List;

@Dao
public interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHabit(HabitEntity habit);

    @Update
    void updateHabit(HabitEntity habit);

    @Delete
    void deleteHabit(HabitEntity habit);

    @Query("SELECT * FROM habits WHERE is_archived = 0 ORDER BY created_at ASC")
    LiveData<List<HabitEntity>> getAllActiveHabits();

    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    HabitEntity getHabitSync(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCompletion(HabitCompletionEntity completion);

    @Delete
    void deleteCompletion(HabitCompletionEntity completion);

    @Query("SELECT * FROM habit_completions WHERE habit_id = :habitId AND completed_date = :date LIMIT 1")
    HabitCompletionEntity getCompletionSync(long habitId, String date);

    @Query("SELECT * FROM habit_completions WHERE completed_date = :date")
    LiveData<List<HabitCompletionEntity>> getCompletionsForDate(String date);

    @Query("SELECT COUNT(*) FROM habit_completions WHERE completed_date = :date")
    LiveData<Integer> getHabitsCompletedCountForDate(String date);

    @Query("SELECT * FROM habit_completions WHERE habit_id = :habitId ORDER BY completed_date DESC")
    LiveData<List<HabitCompletionEntity>> getCompletionsForHabit(long habitId);

    @Query("SELECT COUNT(*) FROM habit_completions WHERE completed_date BETWEEN :start AND :end")
    int getCompletionCountBetween(String start, String end);

    @Query("DELETE FROM habit_completions")
    void deleteAllCompletions();

    @Query("DELETE FROM habits")
    void deleteAllHabits();
}
