package com.app.photoapp.data.local.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_images")
public class SavedImageEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imagePath;
    private String templateUrl;
    private long createdAt;
    private boolean isFavourite;

    public SavedImageEntity() {}

    public SavedImageEntity(String imagePath, String templateUrl, long createdAt, boolean isFavourite) {
        this.imagePath = imagePath;
        this.templateUrl = templateUrl;
        this.createdAt = createdAt;
        this.isFavourite = isFavourite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getTemplateUrl() { return templateUrl; }
    public void setTemplateUrl(String templateUrl) { this.templateUrl = templateUrl; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public boolean isFavourite() { return isFavourite; }
    public void setFavourite(boolean favourite) { isFavourite = favourite; }
}
