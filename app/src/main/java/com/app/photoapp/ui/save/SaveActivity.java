package com.app.photoapp.ui.save;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.app.photoapp.R;
import com.app.photoapp.constants.AppConstants;
import com.app.photoapp.data.local.database.AppDatabase;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.ui.home.MainActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import java.io.File;
import java.util.concurrent.Executors;

public class SaveActivity extends AppCompatActivity {

    private String savedImagePath;
    private String templateUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        savedImagePath = getIntent().getStringExtra(AppConstants.EXTRA_SAVED_IMAGE_PATH);
        templateUrl = getIntent().getStringExtra(AppConstants.EXTRA_TEMPLATE_URL);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Saved!");
        }

        ShapeableImageView imgPreview = findViewById(R.id.img_preview);
        MaterialButton btnShare = findViewById(R.id.btn_share);
        MaterialButton btnFavourite = findViewById(R.id.btn_favourite);

        if (savedImagePath != null) {
            Object source = savedImagePath.startsWith("content://")
                    ? Uri.parse(savedImagePath) : new File(savedImagePath);
            Glide.with(this).load(source).centerCrop().into(imgPreview);
        }

        btnShare.setOnClickListener(v -> shareImage());
        btnFavourite.setOnClickListener(v -> addToFavourites());
    }

    private void shareImage() {
        if (savedImagePath == null) return;
        Uri uri;
        if (savedImagePath.startsWith("content://")) {
            uri = Uri.parse(savedImagePath);
        } else {
            uri = androidx.core.content.FileProvider.getUriForFile(
                    this, getPackageName() + ".provider", new File(savedImagePath));
        }
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    private void addToFavourites() {
        if (savedImagePath == null) return;
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplication());
            SavedImageEntity entity = db.savedImageDao().getByPath(savedImagePath);
            if (entity != null) {
                db.savedImageDao().updateFavouriteStatus(entity.getId(), true);
            } else {
                SavedImageEntity newEntity = new SavedImageEntity(
                        savedImagePath, templateUrl, System.currentTimeMillis(), true);
                db.savedImageDao().insert(newEntity);
            }
            runOnUiThread(() ->
                    Toast.makeText(this, "Added to Favourites!", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
