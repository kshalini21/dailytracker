package com.dailytrack.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.dailytrack.app.data.db.AppDatabase;
import com.dailytrack.app.data.db.dao.*;
import com.dailytrack.app.data.db.entity.*;
import com.dailytrack.app.util.DateUtils;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppRepository {

    private static volatile AppRepository INSTANCE;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    private final UserDao userDao;
    private final TaskDao taskDao;
    private final HabitDao habitDao;
    private final NoteDao noteDao;
    private final ClassScheduleDao scheduleDao;
    private final ExamDao examDao;
    private final FocusSessionDao focusSessionDao;
    private final XPTransactionDao xpTransactionDao;
    private final AchievementDao achievementDao;

    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        taskDao = db.taskDao();
        habitDao = db.habitDao();
        noteDao = db.noteDao();
        scheduleDao = db.classScheduleDao();
        examDao = db.examDao();
        focusSessionDao = db.focusSessionDao();
        xpTransactionDao = db.xpTransactionDao();
        achievementDao = db.achievementDao();
    }

    public static AppRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    // ========== USER ==========
    public LiveData<UserEntity> getUser() { return userDao.getUser(); }
    public void insertOrUpdateUser(UserEntity user) { executor.execute(() -> userDao.insertOrUpdate(user)); }
    public void updateUserName(String name) { executor.execute(() -> userDao.updateName(name)); }
    public void addXpToUser(int xp, int level) { executor.execute(() -> userDao.addXp(xp, level)); }
    public void updateStreak(int streak, int longest, String date) { executor.execute(() -> userDao.updateStreak(streak, longest, date)); }

    // ========== TASKS ==========
    public LiveData<List<TaskEntity>> getAllTasks() { return taskDao.getAllTasks(); }
    public LiveData<List<TaskEntity>> getActiveTasks() { return taskDao.getActiveTasks(); }
    public LiveData<List<TaskEntity>> getCompletedTasks() { return taskDao.getCompletedTasks(); }
    public LiveData<List<TaskEntity>> getTasksForToday() {
        long[] range = DateUtils.getTodayRange();
        return taskDao.getTasksForToday(range[0], range[1]);
    }
    public LiveData<List<TaskEntity>> getActiveTasksForToday() {
        long[] range = DateUtils.getTodayRange();
        return taskDao.getActiveTasksForToday(range[0], range[1]);
    }
    public LiveData<List<TaskEntity>> getDueSoonTasks() { return taskDao.getDueSoonTasks(System.currentTimeMillis()); }
    public LiveData<List<TaskEntity>> searchTasks(String query) { return taskDao.searchTasks("%" + query + "%"); }
    public LiveData<Integer> getCompletedTaskCount() { return taskDao.getCompletedTaskCount(); }
    public LiveData<Integer> getActiveTaskCount() { return taskDao.getActiveTaskCount(); }

    public void insertTask(TaskEntity task) { executor.execute(() -> taskDao.insert(task)); }
    public void updateTask(TaskEntity task) { executor.execute(() -> taskDao.update(task)); }
    public void deleteTask(TaskEntity task) { executor.execute(() -> taskDao.delete(task)); }
    public void setTaskCompleted(long id, boolean done) {
        long time = done ? System.currentTimeMillis() : 0;
        executor.execute(() -> taskDao.setCompleted(id, done, time));
    }

    // ========== HABITS ==========
    public LiveData<List<HabitEntity>> getAllActiveHabits() { return habitDao.getAllActiveHabits(); }
    public LiveData<List<HabitCompletionEntity>> getCompletionsForDate(String date) { return habitDao.getCompletionsForDate(date); }
    public LiveData<Integer> getHabitsCompletedCountForDate(String date) { return habitDao.getHabitsCompletedCountForDate(date); }
    public LiveData<List<HabitCompletionEntity>> getCompletionsForHabit(long habitId) { return habitDao.getCompletionsForHabit(habitId); }

    public void insertHabit(HabitEntity habit) { executor.execute(() -> habitDao.insertHabit(habit)); }
    public void updateHabit(HabitEntity habit) { executor.execute(() -> habitDao.updateHabit(habit)); }
    public void deleteHabit(HabitEntity habit) { executor.execute(() -> habitDao.deleteHabit(habit)); }
    public void toggleHabitCompletion(long habitId, String date) {
        executor.execute(() -> {
            HabitCompletionEntity existing = habitDao.getCompletionSync(habitId, date);
            if (existing != null) {
                habitDao.deleteCompletion(existing);
            } else {
                HabitCompletionEntity completion = new HabitCompletionEntity();
                completion.habitId = habitId;
                completion.completedDate = date;
                habitDao.insertCompletion(completion);
            }
        });
    }

    // ========== NOTES ==========
    public LiveData<List<NoteEntity>> getAllNotes() { return noteDao.getAllNotes(); }
    public LiveData<List<NoteEntity>> getNotesByCategory(String category) { return noteDao.getNotesByCategory(category); }
    public LiveData<List<NoteEntity>> searchNotes(String query) { return noteDao.searchNotes("%" + query + "%"); }
    public LiveData<Integer> getNotesCountForDate(long date) { return noteDao.getNotesCountForDate(date); }

    public void insertNote(NoteEntity note) { executor.execute(() -> noteDao.insert(note)); }
    public void updateNote(NoteEntity note) { executor.execute(() -> noteDao.update(note)); }
    public void deleteNote(NoteEntity note) { executor.execute(() -> noteDao.delete(note)); }

    // ========== SCHEDULE ==========
    public LiveData<List<ClassScheduleEntity>> getAllSchedules() { return scheduleDao.getAllSchedules(); }
    public LiveData<List<ClassScheduleEntity>> getSchedulesForDay(int day) { return scheduleDao.getSchedulesForDay(day); }
    public LiveData<Integer> getClassCountForDay(int day) { return scheduleDao.getClassCountForDay(day); }

    public void insertSchedule(ClassScheduleEntity schedule) { executor.execute(() -> scheduleDao.insert(schedule)); }
    public void updateSchedule(ClassScheduleEntity schedule) { executor.execute(() -> scheduleDao.update(schedule)); }
    public void deleteSchedule(ClassScheduleEntity schedule) { executor.execute(() -> scheduleDao.delete(schedule)); }

    // ========== EXAMS ==========
    public LiveData<List<ExamEntity>> getUpcomingExams() { return examDao.getUpcomingExams(System.currentTimeMillis()); }
    public LiveData<List<ExamEntity>> getAllExams() { return examDao.getAllExams(); }

    public void insertExam(ExamEntity exam) { executor.execute(() -> examDao.insert(exam)); }
    public void updateExam(ExamEntity exam) { executor.execute(() -> examDao.update(exam)); }
    public void deleteExam(ExamEntity exam) { executor.execute(() -> examDao.delete(exam)); }

    // ========== FOCUS SESSIONS ==========
    public LiveData<List<FocusSessionEntity>> getAllFocusSessions() { return focusSessionDao.getAllSessions(); }
    public LiveData<Integer> getTotalFocusMinutes() { return focusSessionDao.getTotalFocusMinutes(); }
    public LiveData<Integer> getTotalSessionCount() { return focusSessionDao.getTotalSessionCount(); }

    public void insertFocusSession(FocusSessionEntity session) { executor.execute(() -> focusSessionDao.insert(session)); }

    // ========== XP TRANSACTIONS ==========
    public LiveData<List<XPTransactionEntity>> getRecentTransactions() { return xpTransactionDao.getRecentTransactions(); }

    public void insertXpTransaction(XPTransactionEntity tx) { executor.execute(() -> xpTransactionDao.insert(tx)); }
    public boolean hasXpTransactionToday(String source, long refId, String date) {
        // Must be called on background thread
        return xpTransactionDao.getTransactionCount(source, refId, date) > 0;
    }

    // ========== ACHIEVEMENTS ==========
    public LiveData<List<AchievementEntity>> getAllAchievements() { return achievementDao.getAllAchievements(); }

    public void initAchievement(String id) {
        executor.execute(() -> {
            AchievementEntity a = new AchievementEntity();
            a.id = id;
            a.unlockedAt = 0;
            achievementDao.insert(a);
        });
    }
    public void unlockAchievement(String id) {
        executor.execute(() -> achievementDao.unlock(id, System.currentTimeMillis()));
    }

    // ========== CLEAR ALL ==========
    public void clearAllData() {
        executor.execute(() -> {
            taskDao.deleteAll();
            habitDao.deleteAllHabits();
            habitDao.deleteAllCompletions();
            noteDao.deleteAll();
            scheduleDao.deleteAll();
            examDao.deleteAll();
            focusSessionDao.deleteAll();
            xpTransactionDao.deleteAll();
            achievementDao.deleteAll();
            userDao.deleteAll();
        });
    }
}
