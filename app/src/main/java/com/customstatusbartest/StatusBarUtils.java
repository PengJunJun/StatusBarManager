package com.customstatusbartest;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by pjj on 2017/6/15.
 */

public class StatusBarUtils {

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    public static int getStatusBarHeight(Context context) {
        Resources res = context.getResources();
        int result = 0;
        int resourceId = res.getIdentifier(STATUS_BAR_HEIGHT_RES_NAME, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
