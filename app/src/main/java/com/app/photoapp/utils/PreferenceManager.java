package com.app.photoapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.app.photoapp.constants.AppConstants;

public class PreferenceManager {

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstLaunch() {
        return prefs.getBoolean(AppConstants.KEY_FIRST_LAUNCH, true);
    }

    public void setFirstLaunchComplete() {
        prefs.edit().putBoolean(AppConstants.KEY_FIRST_LAUNCH, false).apply();
    }

    public void setSelectedTemplateUrl(String url) {
        prefs.edit().putString(AppConstants.KEY_SELECTED_TEMPLATE_URL, url).apply();
    }

    public String getSelectedTemplateUrl() {
        return prefs.getString(AppConstants.KEY_SELECTED_TEMPLATE_URL, "");
    }
}
