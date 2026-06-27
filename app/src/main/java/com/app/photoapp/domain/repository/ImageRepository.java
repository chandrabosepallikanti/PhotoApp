package com.app.photoapp.domain.repository;

import androidx.lifecycle.LiveData;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.data.repository.ImageRepositoryImpl;

import java.util.List;

public interface ImageRepository {
    void saveImage(SavedImageEntity entity, ImageRepositoryImpl.OnCompleteListener listener);
    void toggleFavourite(int id, boolean isFav, ImageRepositoryImpl.OnCompleteListener listener);
    void deleteImage(SavedImageEntity entity);
    LiveData<List<SavedImageEntity>> getAllSavedImages();
    LiveData<List<SavedImageEntity>> getFavouriteImages();
}
