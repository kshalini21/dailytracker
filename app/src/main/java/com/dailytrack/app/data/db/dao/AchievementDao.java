package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.AchievementEntity;
import java.util.List;

@Dao
public interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AchievementEntity achievement);

    @Update
    void update(AchievementEntity achievement);

    @Query("SELECT * FROM achievements ORDER BY unlocked_at ASC")
    LiveData<List<AchievementEntity>> getAllAchievements();

    @Query("SELECT * FROM achievements WHERE id = :achievementId LIMIT 1")
    AchievementEntity getAchievementSync(String achievementId);

    @Query("UPDATE achievements SET unlocked_at = :timestamp WHERE id = :achievementId")
    void unlock(String achievementId, long timestamp);

    @Query("DELETE FROM achievements")
    void deleteAll();
}
