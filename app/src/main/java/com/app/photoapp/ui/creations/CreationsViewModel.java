package com.app.photoapp.ui.creations;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.data.repository.ImageRepositoryImpl;
import com.app.photoapp.domain.repository.ImageRepository;
import java.util.List;

public class CreationsViewModel extends AndroidViewModel {
    private final ImageRepository repository;
    private final LiveData<List<SavedImageEntity>> savedImages;

    public CreationsViewModel(Application application) {
        super(application);
        repository = new ImageRepositoryImpl(application);
        savedImages = repository.getAllSavedImages();
    }

    public LiveData<List<SavedImageEntity>> getSavedImages() { return savedImages; }
}
