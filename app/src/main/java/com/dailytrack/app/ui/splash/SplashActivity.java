package com.dailytrack.app.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import androidx.appcompat.app.AppCompatActivity;
import com.dailytrack.app.databinding.ActivitySplashBinding;
import com.dailytrack.app.ui.main.MainActivity;
import com.dailytrack.app.ui.onboarding.OnboardingActivity;
import com.dailytrack.app.R;

public class SplashActivity extends AppCompatActivity {

    public static final String PREFS = "dailytrack_prefs";
    public static final String KEY_ONBOARDING_DONE = "onboarding_done";

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(800);
        fadeIn.setFillAfter(true);
        binding.layoutContent.startAnimation(fadeIn);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            boolean done = prefs.getBoolean(KEY_ONBOARDING_DONE, false);
            Intent intent = done
                    ? new Intent(this, MainActivity.class)
                    : new Intent(this, OnboardingActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, 2000);
    }
}
