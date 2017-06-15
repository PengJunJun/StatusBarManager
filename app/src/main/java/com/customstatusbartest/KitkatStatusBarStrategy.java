package com.customstatusbartest;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by pjj on 2017/6/15.
 * <p>
 * use in V19 version, change status bar color
 */

public class KitkatStatusBarStrategy implements IStatusBarStrategy {
    private static final String TAG = "KitkatStatusBarStrategy";
    private WeakReference<Activity> mContext;
    private Window mWindow;
    private View mStatusBarView;
    private boolean mIsTranslucent = true;
    private boolean mIsFullScreen = false;

    public KitkatStatusBarStrategy(WeakReference<Activity> context) {
        this.mContext = context;
        this.mWindow = mContext.get().getWindow();
    }

    @Override
    public void translucentStatusBar(boolean fitSystemView) {
        if (!hasTranslucentBarFlag()) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (!mIsTranslucent || mIsFullScreen) {
            removeStatusBarView();
            setContentViewY(0);
            mIsTranslucent = true;
            mIsFullScreen = false;
        }
    }

    @Override
    public void setStatusBarColor(@ColorInt int color, boolean fitSystemView) {
        if (mIsTranslucent) {
            addStatusBarView(color);
            setContentViewY(StatusBarUtils.getStatusBarHeight(mContext.get()));
            mIsTranslucent = false;
        }
    }

    @Override
    public void fullscreenStatusBarColor(@ColorInt int color, boolean fitSystemView) {
        if (mIsTranslucent) {
            addStatusBarView(color);
        } else {
            setContentViewY(0);
            if (mStatusBarView != null) {
                mStatusBarView.setBackgroundColor(color);
            }
            mIsTranslucent = true;
        }
        mIsFullScreen = true;
    }

    private boolean hasTranslucentBarFlag() {
        return (mWindow.getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
    }

    private void addStatusBarView(int color) {
        if (mStatusBarView == null) {
            mStatusBarView = new View(mContext.get());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(StatusBarUtils.getScreenWidth(mContext.get()),
                    StatusBarUtils.getStatusBarHeight(mContext.get()));
            mStatusBarView.setLayoutParams(params);
            ViewGroup viewGroup = (ViewGroup) mWindow.getDecorView().findViewById(android.R.id.content);
            viewGroup.addView(mStatusBarView);
        }
        mStatusBarView.setBackgroundColor(color);
    }

    private void removeStatusBarView() {
        if (mStatusBarView == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) mWindow.getDecorView().findViewById(android.R.id.content);
        viewGroup.removeView(mStatusBarView);
        mStatusBarView = null;
    }

    private void setContentViewY(int distance) {
        ViewGroup viewGroup = (ViewGroup) mWindow.getDecorView().findViewById(android.R.id.content);
        View view = viewGroup.getChildAt(0);
        view.setY(distance);
    }
}