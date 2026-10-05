package com.dailytrack.app.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.dailytrack.app.data.db.entity.ClassScheduleEntity;
import com.dailytrack.app.data.db.entity.TaskEntity;
import com.dailytrack.app.data.db.entity.UserEntity;
import com.dailytrack.app.data.repository.AppRepository;
import com.dailytrack.app.util.DateUtils;
import com.dailytrack.app.util.XpEngine;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final AppRepository repo;

    public final LiveData<UserEntity>              user;
    public final LiveData<List<TaskEntity>>        todayTasks;
    public final LiveData<List<TaskEntity>>        dueSoonTasks;
    public final LiveData<List<ClassScheduleEntity>> todaySchedule;
    public final LiveData<Integer>                 habitsCompletedToday;
    public final LiveData<Integer>                 activeTaskCount;
    public final LiveData<Integer>                 classCountToday;
    public final LiveData<Integer>                 notesCountToday;

    private final MutableLiveData<String> xpToast = new MutableLiveData<>();
    public LiveData<String> getXpToast() { return xpToast; }

    public HomeViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
        user                 = repo.getUser();
        todayTasks           = repo.getTasksForToday();
        dueSoonTasks         = repo.getDueSoonTasks();
        int today            = DateUtils.todayDayOfWeek();
        todaySchedule        = repo.getSchedulesForDay(today);
        habitsCompletedToday = repo.getHabitsCompletedCountForDate(DateUtils.todayString());
        activeTaskCount      = repo.getActiveTaskCount();
        classCountToday      = repo.getClassCountForDay(today);
        notesCountToday      = repo.getNotesCountForDate(System.currentTimeMillis());
    }

    public void toggleTaskComplete(TaskEntity task) {
        boolean nowDone = !task.isCompleted;
        repo.setTaskCompleted(task.id, nowDone);
        if (nowDone) {
            new Thread(() -> {
                boolean awarded = XpEngine.awardXp(repo, "task", task.id,
                        XpEngine.XP_TASK_COMPLETE, "Task completed");
                if (awarded) xpToast.postValue("+" + XpEngine.XP_TASK_COMPLETE + " XP ⭐");
            }).start();
        }
    }

    public int getCompletionPercent(List<TaskEntity> tasks) {
        if (tasks == null || tasks.isEmpty()) return 0;
        long done = tasks.stream().filter(t -> t.isCompleted).count();
        return (int)(done * 100 / tasks.size());
    }
}
