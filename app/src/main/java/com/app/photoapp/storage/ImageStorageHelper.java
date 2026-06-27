package com.app.photoapp.storage;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageStorageHelper {

    private static final String FOLDER_NAME = "PhotoApp";

    public interface OnSaveListener {
        void onSaveComplete(String savedPath);
        void onSaveError(Exception e);
    }

    public static void saveImageToGallery(Context context, String sourcePath, OnSaveListener listener) {
        new Thread(() -> {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(sourcePath);
                if (bitmap == null) {
                    listener.onSaveError(new Exception("Failed to decode image"));
                    return;
                }

                String fileName = "PhotoApp_" + new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + ".jpg";

                String savedPath;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    savedPath = saveToMediaStore(context, bitmap, fileName);
                } else {
                    savedPath = saveToExternalStorage(bitmap, fileName);
                }

                bitmap.recycle();
                if (savedPath != null) {
                    listener.onSaveComplete(savedPath);
                } else {
                    listener.onSaveError(new Exception("Save failed"));
                }
            } catch (Exception e) {
                listener.onSaveError(e);
            }
        }).start();
    }

    private static String saveToMediaStore(Context context, Bitmap bitmap, String fileName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/" + FOLDER_NAME);

        Uri uri = context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri == null) return null;

        try (OutputStream out = context.getContentResolver().openOutputStream(uri)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);
            return uri.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private static String saveToExternalStorage(Bitmap bitmap, String fileName) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), FOLDER_NAME);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
            return file.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }

    public static File createTempImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getCacheDir();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}
