package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habits")
public class HabitEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name = "";

    @ColumnInfo(name = "icon")
    public String icon = "📚"; // emoji icon

    @ColumnInfo(name = "color")
    public String color = "#7B78E4";

    @ColumnInfo(name = "frequency")
    public String frequency = "Daily"; // Daily, Weekly

    @ColumnInfo(name = "is_archived")
    public boolean isArchived = false;

    @ColumnInfo(name = "created_at")
    public long createdAt = System.currentTimeMillis();
}
