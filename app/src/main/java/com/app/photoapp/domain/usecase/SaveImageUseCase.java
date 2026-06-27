package com.app.photoapp.domain.usecase;

import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.data.repository.ImageRepositoryImpl;
import com.app.photoapp.domain.repository.ImageRepository;

public class SaveImageUseCase {
    private final ImageRepository repository;

    public SaveImageUseCase(ImageRepository repository) {
        this.repository = repository;
    }

    public void execute(String imagePath, String templateUrl, ImageRepositoryImpl.OnCompleteListener listener) {
        SavedImageEntity entity = new SavedImageEntity(
                imagePath, templateUrl, System.currentTimeMillis(), false
        );
        repository.saveImage(entity, listener);
    }
}
