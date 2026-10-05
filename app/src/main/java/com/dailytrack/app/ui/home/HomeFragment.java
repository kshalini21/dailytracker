package com.dailytrack.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dailytrack.app.R;
import com.dailytrack.app.adapter.HomeDueSoonAdapter;
import com.dailytrack.app.adapter.HomeScheduleAdapter;
import com.dailytrack.app.adapter.HomeTodayTaskAdapter;
import com.dailytrack.app.databinding.FragmentHomeBinding;
import com.dailytrack.app.ui.exam.ExamActivity;
import com.dailytrack.app.ui.focus.FocusActivity;
import com.dailytrack.app.ui.main.MainActivity;
import com.dailytrack.app.ui.notes.AddEditNoteActivity;
import com.dailytrack.app.ui.profile.ProfileActivity;
import com.dailytrack.app.ui.schedule.ScheduleActivity;
import com.dailytrack.app.ui.tasks.AddEditTaskActivity;
import com.dailytrack.app.util.DateUtils;
import com.dailytrack.app.util.XpEngine;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel vm;
    private HomeTodayTaskAdapter taskAdapter;
    private HomeScheduleAdapter  scheduleAdapter;
    private HomeDueSoonAdapter   dueSoonAdapter;
    private boolean fabOpen = false;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf, @Nullable ViewGroup cont, @Nullable Bundle b) {
        binding = FragmentHomeBinding.inflate(inf, cont, false);
        return binding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        vm = new ViewModelProvider(this).get(HomeViewModel.class);
        setupHeader();
        setupRecyclers();
        setupFab();
        observe();
    }

    private void setupHeader() {
        binding.tvDate.setText(new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(new Date()));
        binding.tvGreeting.setText(DateUtils.getGreeting() + "! 👋");
        binding.ivAvatar.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProfileActivity.class)));
        binding.tvViewAllSchedule.setOnClickListener(v -> startActivity(new Intent(getActivity(), ScheduleActivity.class)));
        binding.tvSeeAllTasks.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) ((MainActivity)getActivity()).navigateToTasks();
        });
    }

    private void setupRecyclers() {
        taskAdapter = new HomeTodayTaskAdapter(t -> vm.toggleTaskComplete(t));
        binding.rvTodayTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTodayTasks.setAdapter(taskAdapter);
        binding.rvTodayTasks.setNestedScrollingEnabled(false);

        scheduleAdapter = new HomeScheduleAdapter(s -> startActivity(new Intent(getActivity(), ScheduleActivity.class)));
        binding.rvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvSchedule.setAdapter(scheduleAdapter);
        binding.rvSchedule.setNestedScrollingEnabled(false);

        dueSoonAdapter = new HomeDueSoonAdapter(t -> {
            Intent i = new Intent(getActivity(), AddEditTaskActivity.class);
            i.putExtra("task_id", t.id);
            startActivity(i);
        });
        binding.rvDueSoon.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDueSoon.setAdapter(dueSoonAdapter);
        binding.rvDueSoon.setNestedScrollingEnabled(false);
    }

    private void setupFab() {
        binding.fab.setOnClickListener(v -> { if (fabOpen) closeFab(); else openFab(); });
        binding.fabTask.setOnClickListener(v ->  { closeFab(); startActivity(new Intent(getActivity(), AddEditTaskActivity.class)); });
        binding.fabHabit.setOnClickListener(v -> { closeFab(); if (getActivity() instanceof MainActivity) ((MainActivity)getActivity()).navigateToHabits(); });
        binding.fabNote.setOnClickListener(v ->  { closeFab(); startActivity(new Intent(getActivity(), AddEditNoteActivity.class)); });
        binding.fabExam.setOnClickListener(v ->  { closeFab(); startActivity(new Intent(getActivity(), ExamActivity.class)); });
        binding.fabFocus.setOnClickListener(v -> { closeFab(); startActivity(new Intent(getActivity(), FocusActivity.class)); });
        binding.fabMenu.setVisibility(View.GONE);
    }

    private void openFab()  { fabOpen=true;  binding.fabMenu.setVisibility(View.VISIBLE); binding.fabMenu.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scale_up)); }
    private void closeFab() { fabOpen=false; binding.fabMenu.setVisibility(View.GONE); }

    private void observe() {
        vm.user.observe(getViewLifecycleOwner(), u -> {
            if (u == null) return;
            binding.tvUserName.setText("Hi " + u.name + "!");
            binding.tvStreakCount.setText("🔥 " + u.streak + " day streak");
            binding.tvXpLevel.setText("Level " + u.level + " · " + XpEngine.getLevelName(u.level));
            binding.tvXpValue.setText(u.totalXp + " / " + XpEngine.getXpForNextLevel(u.level) + " XP");
            binding.progressXp.setProgress((int)(XpEngine.getLevelProgress(u.totalXp)*100));
        });
        vm.todayTasks.observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.submitList(tasks);
            boolean empty = tasks == null || tasks.isEmpty();
            binding.emptyTasks.setVisibility(empty ? View.VISIBLE : View.GONE);
            binding.rvTodayTasks.setVisibility(empty ? View.GONE : View.VISIBLE);
            int pct = vm.getCompletionPercent(tasks);
            binding.progressToday.setProgress(pct);
            int done = tasks==null?0:(int)tasks.stream().filter(t->t.isCompleted).count();
            int total= tasks==null?0:tasks.size();
            binding.tvProgressLabel.setText(done + "/" + total + " completed");
            binding.tvProgressPercent.setText(pct + "%");
        });
        vm.todaySchedule.observe(getViewLifecycleOwner(), s -> {
            scheduleAdapter.submitList(s);
            binding.tvNoSchedule.setVisibility((s==null||s.isEmpty())?View.VISIBLE:View.GONE);
        });
        vm.dueSoonTasks.observe(getViewLifecycleOwner(), t -> {
            dueSoonAdapter.submitList(t);
            binding.tvNoDueSoon.setVisibility((t==null||t.isEmpty())?View.VISIBLE:View.GONE);
        });
        vm.activeTaskCount.observe(getViewLifecycleOwner(),   c -> binding.tvTasksLeft.setText(String.valueOf(c!=null?c:0)));
        vm.habitsCompletedToday.observe(getViewLifecycleOwner(), c -> binding.tvHabitsDone.setText(String.valueOf(c!=null?c:0)));
        vm.classCountToday.observe(getViewLifecycleOwner(),   c -> binding.tvClassesCount.setText(String.valueOf(c!=null?c:0)));
        vm.notesCountToday.observe(getViewLifecycleOwner(),   c -> binding.tvNotesCount.setText(String.valueOf(c!=null?c:0)));
        vm.getXpToast().observe(getViewLifecycleOwner(), msg -> { if (msg!=null&&!msg.isEmpty()) Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show(); });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
