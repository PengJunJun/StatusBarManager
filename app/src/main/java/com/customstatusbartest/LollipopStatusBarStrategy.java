package com.customstatusbartest;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by pjj on 2017/6/15.
 * <p>
 * use in V21 version, change status bar color
 */
public class LollipopStatusBarStrategy implements IStatusBarStrategy {
    private static final String TAG = "LollipopStrategy";
    private WeakReference<Activity> mContext;
    private Window mWindow;
    private boolean mIsTranslucent = true;

    public LollipopStatusBarStrategy(WeakReference<Activity> context) {
        this.mContext = context;
        this.mWindow = mContext.get().getWindow();
        addTranslucentBarFlag();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void translucentStatusBar() {
        addTranslucentBarFlag();
        if (!mIsTranslucent) {
            updateContentViewPaddingTop(0);
            mIsTranslucent = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStatusBarColor(@ColorInt int color) {
        setWindowAttribute(color);
        if (mIsTranslucent) {
            updateContentViewPaddingTop(StatusBarUtils.getStatusBarHeight(mContext.get()));
            mIsTranslucent = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void fullscreenStatusBarColor(@ColorInt int color) {
        setWindowAttribute(color);
        if (!mIsTranslucent) {
            updateContentViewPaddingTop(0);
            mIsTranslucent = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setWindowAttribute(@ColorInt int color) {
        if (hasTranslucentBarFlag()) {
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(color);
    }

    private boolean hasTranslucentBarFlag() {
        return (mWindow.getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
    }

    private void addTranslucentBarFlag() {
        if (!hasTranslucentBarFlag()) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void updateContentViewPaddingTop(int distance) {
        ViewGroup viewGroup = (ViewGroup) mWindow.getDecorView();
        View view = viewGroup.getChildAt(0);
        if (view.getPaddingTop() == distance) {
            return;
        }
        view.setPadding(view.getPaddingLeft()
                , distance
                , view.getPaddingRight()
                , view.getPaddingBottom());
    }
}
