package com.app.photoapp.data.local.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SavedImageEntity entity);

    @Update
    void update(SavedImageEntity entity);

    @Delete
    void delete(SavedImageEntity entity);

    @Query("SELECT * FROM saved_images ORDER BY createdAt DESC")
    LiveData<List<SavedImageEntity>> getAllSavedImages();

    @Query("SELECT * FROM saved_images WHERE isFavourite = 1 ORDER BY createdAt DESC")
    LiveData<List<SavedImageEntity>> getFavouriteImages();

    @Query("SELECT * FROM saved_images WHERE imagePath = :path LIMIT 1")
    SavedImageEntity getByPath(String path);

    @Query("UPDATE saved_images SET isFavourite = :isFav WHERE id = :id")
    void updateFavouriteStatus(int id, boolean isFav);

    @Query("DELETE FROM saved_images WHERE id = :id")
    void deleteById(int id);
}
