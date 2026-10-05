package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exams")
public class ExamEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "subject")
    public String subject = "";

    @ColumnInfo(name = "exam_date")
    public long examDate = 0;

    @ColumnInfo(name = "notes")
    public String notes = "";

    @ColumnInfo(name = "created_at")
    public long createdAt = System.currentTimeMillis();
}
