package com.app.photoapp.ui.result;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.app.photoapp.R;
import com.app.photoapp.constants.AppConstants;
import com.app.photoapp.data.local.database.AppDatabase;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.dialogs.ExitConfirmDialog;
import com.app.photoapp.storage.ImageStorageHelper;
import com.app.photoapp.ui.save.SaveActivity;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.button.MaterialButton;
import java.util.concurrent.Executors;

public class ResultActivity extends AppCompatActivity {

    private String templateUrl;
    private String croppedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        templateUrl = getIntent().getStringExtra(AppConstants.EXTRA_TEMPLATE_URL);
        croppedImagePath = getIntent().getStringExtra(AppConstants.EXTRA_CROPPED_IMAGE_PATH);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Result");
        }

        PhotoView photoView = findViewById(R.id.photo_view);
        MaterialButton btnSave = findViewById(R.id.btn_save);

        if (templateUrl != null && !templateUrl.isEmpty()) {
            Glide.with(this).load(templateUrl).into(photoView);
        }

        btnSave.setOnClickListener(v -> saveImage());
    }

    private void saveImage() {
        Dialog progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.dialog_saving_progress);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String sourcePath = (croppedImagePath != null && !croppedImagePath.isEmpty())
                ? croppedImagePath : null;

        if (sourcePath == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "No cropped image available. Please crop an image first.", Toast.LENGTH_LONG).show();
            return;
        }

        ImageStorageHelper.saveImageToGallery(this, sourcePath, new ImageStorageHelper.OnSaveListener() {
            @Override
            public void onSaveComplete(String path) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    SavedImageEntity entity = new SavedImageEntity(
                            path, templateUrl, System.currentTimeMillis(), false);
                    AppDatabase.getInstance(getApplication()).savedImageDao().insert(entity);

                    runOnUiThread(() -> {
                        progressDialog.dismiss();
                        Toast.makeText(ResultActivity.this, "Image saved successfully!", Toast.LENGTH_SHORT).show();
                        navigateToSave(path);
                    });
                });
            }

            @Override
            public void onSaveError(Exception e) {
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    Toast.makeText(ResultActivity.this,
                            "Failed to save image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void navigateToSave(String path) {
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra(AppConstants.EXTRA_SAVED_IMAGE_PATH, path);
        intent.putExtra(AppConstants.EXTRA_TEMPLATE_URL, templateUrl);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        ExitConfirmDialog dialog = new ExitConfirmDialog();
        dialog.setOnExitListener(() -> finish());
        dialog.show(getSupportFragmentManager(), "exit_confirm");
    }
}
