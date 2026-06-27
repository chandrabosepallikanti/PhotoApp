package com.app.photoapp.utils;

import android.content.Context;
import android.content.res.Configuration;

public class GridSpanHelper {

    public static int getSpanCount(Context context) {
        boolean isTablet = context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
        boolean isLandscape = context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (isTablet) {
            return isLandscape ? 4 : 3;
        } else {
            return isLandscape ? 3 : 2;
        }
    }
}
