package com.app.photoapp.ui.splash;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.app.photoapp.R;
import com.app.photoapp.constants.AppConstants;
import com.app.photoapp.ui.home.MainActivity;
import com.app.photoapp.utils.PreferenceManager;
import com.google.android.material.button.MaterialButton;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvLoading;
    private MaterialButton btnLetsStart;
    private CountDownTimer countDownTimer;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(this);

        if (!preferenceManager.isFirstLaunch()) {
            goToMain();
            return;
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progress_bar);
        tvLoading = findViewById(R.id.tv_loading);
        btnLetsStart = findViewById(R.id.btn_lets_start);

        btnLetsStart.setVisibility(View.GONE);
        tvLoading.setVisibility(View.VISIBLE);

        startLoadingTimer();

        btnLetsStart.setOnClickListener(v -> {
            preferenceManager.setFirstLaunchComplete();
            goToMain();
        });
    }

    private void startLoadingTimer() {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        progressAnimator.setDuration(AppConstants.SPLASH_TIMER_MS);
        progressAnimator.setInterpolator(new DecelerateInterpolator());
        progressAnimator.start();

        countDownTimer = new CountDownTimer(AppConstants.SPLASH_TIMER_MS, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // progress updated by animator
            }

            @Override
            public void onFinish() {
                tvLoading.setVisibility(View.GONE);
                btnLetsStart.setVisibility(View.VISIBLE);
                btnLetsStart.animate().alpha(1f).setDuration(300).start();
            }
        }.start();
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
