package com.app.photoapp.ui.processing;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.app.photoapp.R;
import com.app.photoapp.constants.AppConstants;
import com.app.photoapp.ui.result.ResultActivity;
import com.bumptech.glide.Glide;

public class ProcessingActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private String croppedImagePath;
    private String templateUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        croppedImagePath = getIntent().getStringExtra(AppConstants.EXTRA_CROPPED_IMAGE_PATH);
        templateUrl = getIntent().getStringExtra(AppConstants.EXTRA_TEMPLATE_URL);

        ImageView imgPreview = findViewById(R.id.img_preview);
        progressBar = findViewById(R.id.progress_bar);
        tvProgress = findViewById(R.id.tv_progress);
        TextView tvProcessing = findViewById(R.id.tv_processing);

        if (croppedImagePath != null) {
            Glide.with(this).load(croppedImagePath).centerCrop().into(imgPreview);
        }

        Animation pulseAnim = AnimationUtils.loadAnimation(this, R.anim.pulse);
        tvProcessing.startAnimation(pulseAnim);

        startProcessingTimer();
    }

    private void startProcessingTimer() {
        countDownTimer = new CountDownTimer(AppConstants.PROCESSING_TIMER_MS, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                long elapsed = AppConstants.PROCESSING_TIMER_MS - millisUntilFinished;
                int progress = (int) (elapsed * 100 / AppConstants.PROCESSING_TIMER_MS);
                progressBar.setProgress(progress);
                tvProgress.setText(progress + "%");
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(100);
                tvProgress.setText("100%");
                navigateToResult();
            }
        }.start();
    }

    private void navigateToResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(AppConstants.EXTRA_TEMPLATE_URL, templateUrl);
        intent.putExtra(AppConstants.EXTRA_CROPPED_IMAGE_PATH, croppedImagePath);
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
