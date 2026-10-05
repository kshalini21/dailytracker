package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievements")
public class AchievementEntity {
    @PrimaryKey
    public String id = ""; // first_step, week_streak, study_beast, etc.

    @ColumnInfo(name = "unlocked_at")
    public long unlockedAt = 0; // 0 = locked
}
