package com.app.photoapp;

import android.app.Application;
import com.app.photoapp.data.local.database.AppDatabase;

public class PhotoApp extends Application {

    private static PhotoApp instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);
    }

    public static PhotoApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
