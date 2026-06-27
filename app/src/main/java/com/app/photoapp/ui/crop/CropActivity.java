package com.app.photoapp.ui.crop;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import com.app.photoapp.R;
import com.app.photoapp.constants.AppConstants;
import com.app.photoapp.dialogs.NoFaceDetectedDialog;
import com.app.photoapp.storage.ImageStorageHelper;
import com.app.photoapp.ui.processing.ProcessingActivity;
import com.app.photoapp.utils.PermissionHelper;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.io.IOException;

public class CropActivity extends AppCompatActivity {

    private Uri cameraImageUri;
    private String templateUrl;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedUri = result.getData().getData();
                    if (selectedUri != null) startCrop(selectedUri);
                }
            });

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && cameraImageUri != null) {
                    startCrop(cameraImageUri);
                }
            });

    private final ActivityResultLauncher<Intent> cropLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri resultUri = UCrop.getOutput(result.getData());
                    if (resultUri != null) detectFace(resultUri);
                } else if (result.getResultCode() == UCrop.RESULT_ERROR) {
                    Toast.makeText(this, "Crop failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), result -> {});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        templateUrl = getIntent().getStringExtra(AppConstants.EXTRA_TEMPLATE_URL);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MaterialButton btnGallery = findViewById(R.id.btn_gallery);
        MaterialButton btnCamera = findViewById(R.id.btn_camera);
        btnGallery.setOnClickListener(v -> pickFromGallery());
        btnCamera.setOnClickListener(v -> captureFromCamera());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void pickFromGallery() {
        if (!PermissionHelper.hasStoragePermission(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(new String[]{Manifest.permission.READ_MEDIA_IMAGES});
            } else {
                permissionLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
            }
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void captureFromCamera() {
        if (!PermissionHelper.hasCameraPermission(this)) {
            permissionLauncher.launch(new String[]{Manifest.permission.CAMERA});
            return;
        }
        try {
            File photoFile = ImageStorageHelper.createTempImageFile(this);
            cameraImageUri = FileProvider.getUriForFile(this,
                    getApplicationContext().getPackageName() + ".provider", photoFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            cameraLauncher.launch(intent);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
        }
    }

    private void startCrop(Uri sourceUri) {
        try {
            File destFile = ImageStorageHelper.createTempImageFile(this);
            Uri destUri = Uri.fromFile(destFile);

            UCrop.Options options = new UCrop.Options();
            options.setToolbarTitle("Crop Image");
            options.setShowCropGrid(true);
            options.setShowCropFrame(true);
            options.setFreeStyleCropEnabled(true);

            Intent intent = UCrop.of(sourceUri, destUri)
                    .withOptions(options)
                    .withMaxResultSize(1080, 1080)
                    .getIntent(this);

            cropLauncher.launch(intent);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to prepare cropping", Toast.LENGTH_SHORT).show();
        }
    }

    private void detectFace(Uri imageUri) {
        try {
            InputImage image = InputImage.fromFilePath(this, imageUri);
            FaceDetectorOptions opts = new FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                    .build();

            FaceDetector detector = FaceDetection.getClient(opts);
            detector.process(image)
                    .addOnSuccessListener(faces -> {
                        if (faces.isEmpty()) {
                            NoFaceDetectedDialog dialog = new NoFaceDetectedDialog();
                            dialog.show(getSupportFragmentManager(), "no_face");
                        } else {
                            navigateToProcessing(imageUri.getPath());
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Face detection failed. Proceeding.", Toast.LENGTH_SHORT).show();
                        navigateToProcessing(imageUri.getPath());
                    });
        } catch (IOException e) {
            Toast.makeText(this, "Failed to load image for detection", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToProcessing(String croppedPath) {
        Intent intent = new Intent(this, ProcessingActivity.class);
        intent.putExtra(AppConstants.EXTRA_CROPPED_IMAGE_PATH, croppedPath);
        intent.putExtra(AppConstants.EXTRA_TEMPLATE_URL, templateUrl);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
