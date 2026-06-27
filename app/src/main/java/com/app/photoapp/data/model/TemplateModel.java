package com.app.photoapp.data.model;

import com.google.gson.annotations.SerializedName;

public class TemplateModel {

    @SerializedName("isnew")
    private String isNew;

    @SerializedName("temp_type")
    private String tempType;

    @SerializedName("image_url")
    private String imageUrl;

    public TemplateModel() {}

    public TemplateModel(String isNew, String tempType, String imageUrl) {
        this.isNew = isNew;
        this.tempType = tempType;
        this.imageUrl = imageUrl;
    }

    public String getIsNew() { return isNew; }
    public void setIsNew(String isNew) { this.isNew = isNew; }

    public String getTempType() { return tempType; }
    public void setTempType(String tempType) { this.tempType = tempType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isNewTemplate() {
        return "true".equalsIgnoreCase(isNew);
    }

    public boolean isFree() {
        return "free".equalsIgnoreCase(tempType);
    }
}
