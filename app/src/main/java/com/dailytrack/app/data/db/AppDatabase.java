package com.dailytrack.app.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.dailytrack.app.data.db.dao.*;
import com.dailytrack.app.data.db.entity.*;

@Database(
    entities = {
        UserEntity.class,
        TaskEntity.class,
        HabitEntity.class,
        HabitCompletionEntity.class,
        NoteEntity.class,
        ClassScheduleEntity.class,
        ExamEntity.class,
        FocusSessionEntity.class,
        XPTransactionEntity.class,
        AchievementEntity.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    public abstract HabitDao habitDao();
    public abstract NoteDao noteDao();
    public abstract ClassScheduleDao classScheduleDao();
    public abstract ExamDao examDao();
    public abstract FocusSessionDao focusSessionDao();
    public abstract XPTransactionDao xpTransactionDao();
    public abstract AchievementDao achievementDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "dailytrack_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
