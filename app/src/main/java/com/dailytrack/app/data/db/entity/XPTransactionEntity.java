package com.dailytrack.app.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "xp_transactions")
public class XPTransactionEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "amount")
    public int amount = 0;

    @ColumnInfo(name = "source")
    public String source = ""; // task, habit, focus, note

    @ColumnInfo(name = "description")
    public String description = "";

    @ColumnInfo(name = "reference_id")
    public long referenceId = 0;

    @ColumnInfo(name = "created_at")
    public long createdAt = System.currentTimeMillis();

    @ColumnInfo(name = "date")
    public String date = ""; // yyyy-MM-dd
}
