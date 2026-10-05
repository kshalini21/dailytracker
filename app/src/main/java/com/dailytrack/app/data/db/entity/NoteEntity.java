package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String title = "";

    @ColumnInfo(name = "content")
    public String content = "";

    @ColumnInfo(name = "category")
    public String category = "College"; // College, Personal, Ideas, Projects

    @ColumnInfo(name = "created_at")
    public long createdAt = System.currentTimeMillis();

    @ColumnInfo(name = "updated_at")
    public long updatedAt = System.currentTimeMillis();
}
