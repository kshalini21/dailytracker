package com.dailytrack.app.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.dailytrack.app.R;
import com.dailytrack.app.databinding.ActivityMainBinding;
import com.dailytrack.app.ui.habits.HabitsFragment;
import com.dailytrack.app.ui.home.HomeFragment;
import com.dailytrack.app.ui.notes.NotesFragment;
import com.dailytrack.app.ui.tasks.TasksFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment current;

    private final HomeFragment    homeFragment    = new HomeFragment();
    private final TasksFragment   tasksFragment   = new TasksFragment();
    private final HabitsFragment  habitsFragment  = new HabitsFragment();
    private final NotesFragment   notesFragment   = new NotesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if      (id == R.id.nav_home)   showFragment(homeFragment);
            else if (id == R.id.nav_tasks)  showFragment(tasksFragment);
            else if (id == R.id.nav_habits) showFragment(habitsFragment);
            else if (id == R.id.nav_notes)  showFragment(notesFragment);
            return true;
        });

        if (savedInstanceState == null) showFragment(homeFragment);
    }

    private void showFragment(Fragment f) {
        if (current == f) return;
        var tx = getSupportFragmentManager().beginTransaction();
        if (!f.isAdded()) tx.add(R.id.fragment_container, f);
        else tx.show(f);
        if (current != null) tx.hide(current);
        tx.commit();
        current = f;
    }

    public void navigateToTasks()  { binding.bottomNav.setSelectedItemId(R.id.nav_tasks); }
    public void navigateToHabits() { binding.bottomNav.setSelectedItemId(R.id.nav_habits); }
    public void navigateToNotes()  { binding.bottomNav.setSelectedItemId(R.id.nav_notes); }
}
