package com.customstatusbartest;

import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by pjj on 2017/6/14.
 * <p>
 * status bar manager,
 */

public class StatusBarManager {
    private static final String TAG = "StatusBarManager";
    static StatusBarManager mStatusBarManager;
    private StatusBarConfig mStatusBarConfig;
    private WeakReference<IStatusBarCallback> mContext;
    private int mStatusBarColor = -1;
    private boolean mIsFullscreen;
    private int mFullscreenColor = -1;

    private StatusBarManager(IStatusBarCallback context) {
        this.mContext = new WeakReference<>(context);
    }

    public static StatusBarManager with(IStatusBarCallback context) {
        if (mStatusBarManager == null) {
            synchronized (StatusBarManager.class) {
                if (mStatusBarManager == null) {
                    mStatusBarManager = new StatusBarManager(context);
                }
            }
        }
        return mStatusBarManager;
    }

    public StatusBarManager statusBarColor(int color) {
        this.mStatusBarColor = color;
        return this;
    }

    public StatusBarManager fullscreen(boolean fullscreen) {
        this.mIsFullscreen = fullscreen;
        return this;
    }

    public StatusBarManager fullscreenColor(int color) {
        this.mFullscreenColor = color;
        return this;
    }

    private void initStatusBarConfig() {
        mFullscreenColor = -1;
        mStatusBarColor = -1;
        mIsFullscreen = false;
    }

    public void apply() {
        if (mContext == null) {
            Log.e(TAG, "StatusBarManager error, mContext can not null");
            return;
        }
        if (mStatusBarConfig == null) {
            mStatusBarConfig = new StatusBarConfig.Builder().build();
        }
        mStatusBarConfig.setIsFullscreen(mIsFullscreen);
        mStatusBarConfig.setStatusBarColor(mStatusBarColor);
        mStatusBarConfig.setFullscreenColor(mFullscreenColor);
        mContext.get().onStatusBarConfigChange(mStatusBarConfig);
        initStatusBarConfig();
    }
}
