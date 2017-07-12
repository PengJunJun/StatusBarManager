package com.customstatusbartest;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by pjj on 2017/6/15.
 * <p>
 * use in V21 version, change status bar color
 */
public class LollipopStatusBarStrategy implements IStatusBarStrategy {
    private WeakReference<Activity> mContext;
    private Window mWindow;
    private boolean mIsTranslucent = true;

    public LollipopStatusBarStrategy(WeakReference<Activity> context) {
        this.mContext = context;
        this.mWindow = mContext.get().getWindow();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void translucentStatusBar(boolean fitSystemView) {
        int flag = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (!hasTranslucentBarFlag()) {
            mWindow.addFlags(flag);
        }
        if (!mIsTranslucent) {
            mWindow.getDecorView().findViewById(android.R.id.content).setY(0);
            mIsTranslucent = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStatusBarColor(@ColorInt int color, boolean fitSystemView) {
        setStatusBarColor(color);
        if (mIsTranslucent) {
            mWindow.getDecorView().findViewById(android.R.id.content).setY(StatusBarUtils.getStatusBarHeight(mContext.get()));
            mIsTranslucent = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void fullscreenStatusBarColor(@ColorInt int color, boolean fitSystemView) {
        setStatusBarColor(color);
        if (!mIsTranslucent) {
            mWindow.getDecorView().findViewById(android.R.id.content).setY(0);
            mIsTranslucent = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(@ColorInt int color) {
        if (hasTranslucentBarFlag()) {
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(color);
        mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private boolean hasTranslucentBarFlag() {
        return (mWindow.getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
    }
}
