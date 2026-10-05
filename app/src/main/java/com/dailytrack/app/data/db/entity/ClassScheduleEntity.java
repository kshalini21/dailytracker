package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "class_schedules")
public class ClassScheduleEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "subject")
    public String subject = "";

    @ColumnInfo(name = "teacher")
    public String teacher = "";

    @ColumnInfo(name = "room")
    public String room = "";

    @ColumnInfo(name = "start_time")
    public String startTime = ""; // HH:mm

    @ColumnInfo(name = "end_time")
    public String endTime = ""; // HH:mm

    @ColumnInfo(name = "day_of_week")
    public int dayOfWeek = 1; // 1=Monday, 2=Tuesday, ... 7=Sunday

    @ColumnInfo(name = "color")
    public String color = "#7B78E4";
}
