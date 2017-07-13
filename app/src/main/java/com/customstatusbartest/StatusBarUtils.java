package com.customstatusbartest;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;

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

    public static boolean isSupportToolBar(Context activity) {
        if (activity != null) {
            if (activity instanceof AppCompatActivity) {
                return ((AppCompatActivity) activity).getSupportActionBar() != null;
            }
        }
        return false;
    }

    public static int getToolBarHeight(Context context) {
        int[] attrs = new int[]{R.attr.actionBarSize};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }
}
