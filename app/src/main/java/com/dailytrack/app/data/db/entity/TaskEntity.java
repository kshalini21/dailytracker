package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String title = "";

    @ColumnInfo(name = "description")
    public String description = "";

    @ColumnInfo(name = "due_date")
    public long dueDate = 0;

    @ColumnInfo(name = "priority")
    public String priority = "Medium"; // Low, Medium, High

    @ColumnInfo(name = "category")
    public String category = "College"; // College, Study, Coding, Assignment, Lab, Project, Personal

    @ColumnInfo(name = "is_completed")
    public boolean isCompleted = false;

    @ColumnInfo(name = "completed_at")
    public long completedAt = 0;

    @ColumnInfo(name = "created_at")
    public long createdAt = System.currentTimeMillis();
}
