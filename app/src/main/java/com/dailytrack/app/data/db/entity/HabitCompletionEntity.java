package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habit_completions")
public class HabitCompletionEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "habit_id")
    public long habitId;

    @ColumnInfo(name = "completed_date")
    public String completedDate = ""; // format: yyyy-MM-dd

    @ColumnInfo(name = "completed_at")
    public long completedAt = System.currentTimeMillis();
}
