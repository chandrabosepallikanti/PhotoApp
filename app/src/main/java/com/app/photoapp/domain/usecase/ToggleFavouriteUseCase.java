package com.app.photoapp.domain.usecase;

import com.app.photoapp.data.repository.ImageRepositoryImpl;
import com.app.photoapp.domain.repository.ImageRepository;

public class ToggleFavouriteUseCase {
    private final ImageRepository repository;

    public ToggleFavouriteUseCase(ImageRepository repository) {
        this.repository = repository;
    }

    public void execute(int id, boolean isFav, ImageRepositoryImpl.OnCompleteListener listener) {
        repository.toggleFavourite(id, isFav, listener);
    }
}
