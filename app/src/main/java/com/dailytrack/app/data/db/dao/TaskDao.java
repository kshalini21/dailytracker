package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.TaskEntity;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TaskEntity task);

    @Update
    void update(TaskEntity task);

    @Delete
    void delete(TaskEntity task);

    @Query("SELECT * FROM tasks ORDER BY created_at DESC")
    LiveData<List<TaskEntity>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE is_completed = 0 ORDER BY due_date ASC")
    LiveData<List<TaskEntity>> getActiveTasks();

    @Query("SELECT * FROM tasks WHERE is_completed = 1 ORDER BY completed_at DESC")
    LiveData<List<TaskEntity>> getCompletedTasks();

    @Query("SELECT * FROM tasks WHERE due_date >= :startOfDay AND due_date < :endOfDay ORDER BY due_date ASC")
    LiveData<List<TaskEntity>> getTasksForToday(long startOfDay, long endOfDay);

    @Query("SELECT * FROM tasks WHERE due_date >= :startOfDay AND due_date < :endOfDay AND is_completed = 0")
    LiveData<List<TaskEntity>> getActiveTasksForToday(long startOfDay, long endOfDay);

    @Query("SELECT * FROM tasks WHERE due_date > :now AND is_completed = 0 ORDER BY due_date ASC LIMIT 5")
    LiveData<List<TaskEntity>> getDueSoonTasks(long now);

    @Query("SELECT * FROM tasks WHERE due_date >= :start AND due_date < :end AND is_completed = 0 ORDER BY due_date ASC")
    LiveData<List<TaskEntity>> getUpcomingTasks(long start, long end);

    @Query("SELECT COUNT(*) FROM tasks WHERE is_completed = 1")
    LiveData<Integer> getCompletedTaskCount();

    @Query("SELECT COUNT(*) FROM tasks WHERE is_completed = 0")
    LiveData<Integer> getActiveTaskCount();

    @Query("SELECT * FROM tasks WHERE title LIKE :query OR description LIKE :query ORDER BY created_at DESC")
    LiveData<List<TaskEntity>> searchTasks(String query);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("UPDATE tasks SET is_completed = :done, completed_at = :completedAt WHERE id = :id")
    void setCompleted(long id, boolean done, long completedAt);
}
