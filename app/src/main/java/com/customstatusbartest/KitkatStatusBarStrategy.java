package com.customstatusbartest;

import android.app.Activity;
import android.support.annotation.ColorInt;
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
    private WeakReference<Activity> mContext;
    private Window mWindow;
    private View mStatusBarView;
    private boolean mIsTranslucent = true;
    private boolean mIsFullScreen = false;

    public KitkatStatusBarStrategy(WeakReference<Activity> context) {
        this.mContext = context;
        this.mWindow = mContext.get().getWindow();
        addTranslucentBarFlag();
    }

    @Override
    public void translucentStatusBar() {
        if (!mIsTranslucent || mIsFullScreen) {
            removeStatusBarView();
            mIsTranslucent = true;
            mIsFullScreen = false;
        }
    }

    @Override
    public void setStatusBarColor(@ColorInt int color) {
        addStatusBarView(color);
        if (mIsTranslucent) {
            updateContentViewPaddingTop(StatusBarUtils.getStatusBarHeight(mContext.get()));
            mIsTranslucent = false;
        }
    }

    @Override
    public void fullscreenStatusBarColor(@ColorInt int color) {
        if (mIsTranslucent) {
            addStatusBarView(color);
        } else {
            if (mStatusBarView != null) {
                updateContentViewPaddingTop(0);
                mStatusBarView.setBackgroundColor(color);
            }
            mIsTranslucent = true;
        }
        mIsFullScreen = true;
    }

    private boolean hasTranslucentBarFlag() {
        return (mWindow.getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
    }

    private void addTranslucentBarFlag() {
        if (!hasTranslucentBarFlag()) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void addStatusBarView(int color) {
        if (mStatusBarView == null) {
            mStatusBarView = new View(mContext.get());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(StatusBarUtils.getScreenWidth(mContext.get()),
                    StatusBarUtils.getStatusBarHeight(mContext.get()));
            mStatusBarView.setLayoutParams(params);
            ViewGroup viewGroup = (ViewGroup) mWindow.getDecorView();
            viewGroup.addView(mStatusBarView);
        }
        mStatusBarView.setBackgroundColor(color);
    }

    private void removeStatusBarView() {
        if (mStatusBarView == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) mWindow.getDecorView();
        viewGroup.removeView(mStatusBarView);
        updateContentViewPaddingTop(0);
        mStatusBarView = null;
    }

    private void updateContentViewPaddingTop(int distance) {
        ViewGroup viewGroup = (ViewGroup) mWindow.getDecorView().findViewById(android.R.id.content);
        if (viewGroup.getPaddingTop() == distance) {
            return;
        }
        viewGroup.setPadding(viewGroup.getPaddingLeft()
                , distance
                , viewGroup.getPaddingRight()
                , viewGroup.getPaddingBottom());
    }
}
