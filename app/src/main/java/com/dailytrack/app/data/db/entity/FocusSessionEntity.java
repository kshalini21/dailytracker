package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "focus_sessions")
public class FocusSessionEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "duration_minutes")
    public int durationMinutes = 25;

    @ColumnInfo(name = "activity")
    public String activity = "";

    @ColumnInfo(name = "completed_at")
    public long completedAt = System.currentTimeMillis();

    @ColumnInfo(name = "date")
    public String date = ""; // yyyy-MM-dd
}
