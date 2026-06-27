package com.app.photoapp.ui.favourites;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.app.photoapp.data.repository.ImageRepositoryImpl;
import com.app.photoapp.domain.repository.ImageRepository;
import java.util.List;

public class FavouritesViewModel extends AndroidViewModel {
    private final ImageRepository repository;
    private final LiveData<List<SavedImageEntity>> favourites;

    public FavouritesViewModel(Application application) {
        super(application);
        repository = new ImageRepositoryImpl(application);
        favourites = repository.getFavouriteImages();
    }

    public LiveData<List<SavedImageEntity>> getFavourites() { return favourites; }
}
