package com.dailytrack.app;

import android.app.Application;
import com.dailytrack.app.data.db.entity.AchievementEntity;
import com.dailytrack.app.data.repository.AppRepository;

public class DailyTrackApp extends Application {

    private static final String[] ACHIEVEMENT_IDS = {
            "first_step",
            "week_streak",
            "consistency",
            "study_beast",
            "coding_streak",
            "habit_master",
            "focus_novice",
            "focus_pro",
            "note_taker",
            "task_finisher"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize achievement rows (if not already present)
        initAchievements();
    }

    private void initAchievements() {
        AppRepository repo = AppRepository.getInstance(this);
        for (String id : ACHIEVEMENT_IDS) {
            repo.initAchievement(id);
        }
    }
}
