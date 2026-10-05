package com.dailytrack.app.ui.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.dailytrack.app.data.db.entity.UserEntity;
import com.dailytrack.app.data.repository.AppRepository;
import com.dailytrack.app.databinding.ActivityOnboardingBinding;
import com.dailytrack.app.ui.main.MainActivity;
import com.dailytrack.app.ui.splash.SplashActivity;
import com.dailytrack.app.R;

public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;
    private OnboardingAdapter adapter;
    private static final int TOTAL_PAGES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new OnboardingAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) { updateUI(position); }
        });

        updateUI(0);

        binding.btnNext.setOnClickListener(v -> {
            int cur = binding.viewPager.getCurrentItem();
            if (cur < TOTAL_PAGES - 1) binding.viewPager.setCurrentItem(cur + 1);
            else finish();
        });
        binding.btnSkip.setOnClickListener(v -> finish());
    }

    private void updateUI(int pos) {
        // Dots
        binding.dotsContainer.removeAllViews();
        for (int i = 0; i < TOTAL_PAGES; i++) {
            View dot = new View(this);
            android.widget.LinearLayout.LayoutParams p =
                new android.widget.LinearLayout.LayoutParams(i == pos ? 24 : 8, 8);
            p.setMargins(4, 0, 4, 0);
            dot.setLayoutParams(p);
            dot.setBackgroundResource(i == pos ? R.drawable.bg_chip_selected : R.drawable.bg_chip_unselected);
            binding.dotsContainer.addView(dot);
        }
        if (pos == TOTAL_PAGES - 1) {
            binding.btnNext.setText("Get Started →");
            binding.btnSkip.setVisibility(View.GONE);
        } else {
            binding.btnNext.setText("Next");
            binding.btnSkip.setVisibility(View.VISIBLE);
        }
    }

    @Override public void finish() {
        String name = adapter != null ? adapter.getEnteredName() : "";
        if (TextUtils.isEmpty(name)) name = "Student";

        getSharedPreferences(SplashActivity.PREFS, MODE_PRIVATE).edit()
                .putBoolean(SplashActivity.KEY_ONBOARDING_DONE, true)
                .putString("user_name", name)
                .apply();

        UserEntity user = new UserEntity();
        user.id = 1L; user.name = name;
        AppRepository.getInstance(getApplication()).insertOrUpdateUser(user);

        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
        super.finish();
    }
}
