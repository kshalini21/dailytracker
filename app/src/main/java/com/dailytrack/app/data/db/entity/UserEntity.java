package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    public long id = 1L; // Single user, always id=1

    @ColumnInfo(name = "name")
    public String name = "Student";

    @ColumnInfo(name = "join_date")
    public long joinDate = System.currentTimeMillis();

    @ColumnInfo(name = "total_xp")
    public int totalXp = 0;

    @ColumnInfo(name = "level")
    public int level = 1;

    @ColumnInfo(name = "streak")
    public int streak = 0;

    @ColumnInfo(name = "longest_streak")
    public int longestStreak = 0;

    @ColumnInfo(name = "last_active_date")
    public String lastActiveDate = "";
}
