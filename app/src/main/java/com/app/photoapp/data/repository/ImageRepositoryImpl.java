package com.app.photoapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.app.photoapp.data.local.database.AppDatabase;
import com.app.photoapp.data.local.database.SavedImageDao;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.domain.repository.ImageRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageRepositoryImpl implements ImageRepository {

    private final SavedImageDao dao;
    private final ExecutorService executor;

    public ImageRepositoryImpl(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        dao = db.savedImageDao();
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void saveImage(SavedImageEntity entity, OnCompleteListener listener) {
        executor.execute(() -> {
            long id = dao.insert(entity);
            if (listener != null) listener.onComplete(id > 0);
        });
    }

    @Override
    public void toggleFavourite(int id, boolean isFav, OnCompleteListener listener) {
        executor.execute(() -> {
            dao.updateFavouriteStatus(id, isFav);
            if (listener != null) listener.onComplete(true);
        });
    }

    @Override
    public void deleteImage(SavedImageEntity entity) {
        executor.execute(() -> dao.delete(entity));
    }

    @Override
    public LiveData<List<SavedImageEntity>> getAllSavedImages() {
        return dao.getAllSavedImages();
    }

    @Override
    public LiveData<List<SavedImageEntity>> getFavouriteImages() {
        return dao.getFavouriteImages();
    }

    public interface OnCompleteListener {
        void onComplete(boolean success);
    }
}
