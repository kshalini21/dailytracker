package com.dailytrack.app.util;

import android.app.Application;
import com.dailytrack.app.data.db.entity.UserEntity;
import com.dailytrack.app.data.db.entity.XPTransactionEntity;
import com.dailytrack.app.data.repository.AppRepository;

/**
 * XP and level calculation engine.
 * All XP award logic is centralized here.
 */
public class XpEngine {

    // XP rewards — configurable
    public static final int XP_TASK_COMPLETE = 20;
    public static final int XP_HABIT_COMPLETE = 15;
    public static final int XP_FOCUS_SESSION = 30;
    public static final int XP_NOTE_CREATED = 5;

    // Level thresholds (cumulative XP needed)
    private static final int[] LEVEL_THRESHOLDS = {
            0,    // Level 1: Starter
            200,  // Level 2: Getting Started
            500,  // Level 3: Consistent
            1000, // Level 4: Habit Builder
            2000, // Level 5: Daily Champion
            3500, // Level 6: Elite Student
            5500, // Level 7: Legend
            8000, // Level 8: Unstoppable
            11000,// Level 9: Master
            15000 // Level 10: Grandmaster
    };

    private static final String[] LEVEL_NAMES = {
            "Starter",
            "Getting Started",
            "Consistent",
            "Habit Builder",
            "Daily Champion",
            "Elite Student",
            "Legend",
            "Unstoppable",
            "Master",
            "Grandmaster"
    };

    /**
     * Calculate level from total XP.
     * @return level (1-based)
     */
    public static int calculateLevel(int totalXp) {
        int level = 1;
        for (int i = LEVEL_THRESHOLDS.length - 1; i >= 0; i--) {
            if (totalXp >= LEVEL_THRESHOLDS[i]) {
                level = i + 1;
                break;
            }
        }
        return level;
    }

    /** Get level name */
    public static String getLevelName(int level) {
        int idx = Math.max(0, Math.min(level - 1, LEVEL_NAMES.length - 1));
        return LEVEL_NAMES[idx];
    }

    /** XP required for a specific level */
    public static int getXpForLevel(int level) {
        int idx = Math.max(0, Math.min(level - 1, LEVEL_THRESHOLDS.length - 1));
        return LEVEL_THRESHOLDS[idx];
    }

    /** XP needed for next level */
    public static int getXpForNextLevel(int level) {
        if (level >= LEVEL_THRESHOLDS.length) return LEVEL_THRESHOLDS[LEVEL_THRESHOLDS.length - 1];
        return LEVEL_THRESHOLDS[level];
    }

    /** Progress (0.0 to 1.0) toward next level */
    public static float getLevelProgress(int totalXp) {
        int level = calculateLevel(totalXp);
        int currentLevelXp = getXpForLevel(level);
        int nextLevelXp = getXpForNextLevel(level);
        if (nextLevelXp <= currentLevelXp) return 1.0f;
        return (float)(totalXp - currentLevelXp) / (nextLevelXp - currentLevelXp);
    }

    /**
     * Award XP to the user. Must be called on a background thread.
     * Prevents duplicate XP for the same action on the same day.
     *
     * @param repo       The app repository
     * @param source     Source type (e.g., "task", "habit", "focus", "note")
     * @param refId      The entity ID (task.id, habit.id, etc.) — 0 if N/A
     * @param amount     XP amount to award
     * @param description Human-readable description
     * @return true if XP was awarded, false if duplicate
     */
    public static boolean awardXp(AppRepository repo, String source, long refId, int amount, String description) {
        String today = DateUtils.todayString();

        // Duplicate check for tasks/habits — one award per entity per day
        if (refId > 0 && repo.hasXpTransactionToday(source, refId, today)) {
            return false; // Already awarded today
        }

        // Insert XP transaction
        XPTransactionEntity tx = new XPTransactionEntity();
        tx.amount = amount;
        tx.source = source;
        tx.description = description;
        tx.referenceId = refId;
        tx.date = today;
        tx.createdAt = System.currentTimeMillis();
        repo.insertXpTransaction(tx);

        // Update user total XP + recalculate level
        // NOTE: This runs on background thread via executor internally
        // We need to get current user synchronously — done via getUser() observe on main,
        // but here we'll use a direct approach:
        // We increment via DAO atomic operation
        // Get current total from a sync query — the DAO method handles this
        repo.addXpToUser(amount, 0); // level will be recalculated in ViewModel observe

        return true;
    }

    /** Achievement check constants */
    public static final String ACH_FIRST_STEP = "first_step";
    public static final String ACH_WEEK_STREAK = "week_streak";
    public static final String ACH_CONSISTENCY = "consistency";
    public static final String ACH_STUDY_BEAST = "study_beast";
    public static final String ACH_CODING_STREAK = "coding_streak";
    public static final String ACH_HABIT_MASTER = "habit_master";
    public static final String ACH_FOCUS_NOVICE = "focus_novice";
    public static final String ACH_FOCUS_PRO = "focus_pro";
    public static final String ACH_NOTE_TAKER = "note_taker";
    public static final String ACH_TASK_FINISHER = "task_finisher";
}
