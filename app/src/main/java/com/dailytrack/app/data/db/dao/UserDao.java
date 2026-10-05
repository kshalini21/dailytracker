package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.UserEntity;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(UserEntity user);

    @Update
    void update(UserEntity user);

    @Query("SELECT * FROM users WHERE id = 1 LIMIT 1")
    LiveData<UserEntity> getUser();

    @Query("SELECT * FROM users WHERE id = 1 LIMIT 1")
    UserEntity getUserSync();

    @Query("UPDATE users SET total_xp = total_xp + :xp, level = :level WHERE id = 1")
    void addXp(int xp, int level);

    @Query("UPDATE users SET name = :name WHERE id = 1")
    void updateName(String name);

    @Query("UPDATE users SET streak = :streak, longest_streak = :longest, last_active_date = :date WHERE id = 1")
    void updateStreak(int streak, int longest, String date);

    @Query("DELETE FROM users")
    void deleteAll();
}
