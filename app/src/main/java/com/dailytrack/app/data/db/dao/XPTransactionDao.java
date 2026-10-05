package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.XPTransactionEntity;
import java.util.List;

@Dao
public interface XPTransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(XPTransactionEntity transaction);

    @Query("SELECT * FROM xp_transactions ORDER BY created_at DESC LIMIT 50")
    LiveData<List<XPTransactionEntity>> getRecentTransactions();

    @Query("SELECT SUM(amount) FROM xp_transactions WHERE date = :date")
    int getXpForDate(String date);

    @Query("SELECT COUNT(*) FROM xp_transactions WHERE source = :source AND reference_id = :refId AND date = :date")
    int getTransactionCount(String source, long refId, String date);

    @Query("DELETE FROM xp_transactions")
    void deleteAll();
}
