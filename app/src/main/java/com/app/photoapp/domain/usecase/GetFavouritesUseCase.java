package com.app.photoapp.domain.usecase;

import androidx.lifecycle.LiveData;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.domain.repository.ImageRepository;

import java.util.List;

public class GetFavouritesUseCase {
    private final ImageRepository repository;

    public GetFavouritesUseCase(ImageRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<SavedImageEntity>> execute() {
        return repository.getFavouriteImages();
    }
}
