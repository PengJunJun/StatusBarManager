package com.customstatusbartest;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pjj on 2017/6/14.
 * <p>
 * status bar manager,
 */

public class StatusBarManager {
    private static final String TAG = "StatusBarManager";
    private StatusBarConfig.Builder mBuilder;
    private WeakReference<IStatusBarCallback> mContext;

    public StatusBarManager(IStatusBarCallback context) {
        this.mContext = new WeakReference<>(context);
        this.mBuilder = new StatusBarConfig.Builder();
    }

    public static StatusBarManager with(IStatusBarCallback context) {
        return StatusBarFactory.getInstance().create(context);
    }

    public StatusBarManager statusBarColor(int color) {
        this.mBuilder.statusBarColor(color);
        return this;
    }

    public StatusBarManager fullscreen(boolean fullscreen) {
        this.mBuilder.fullscreen(fullscreen);
        return this;
    }

    public StatusBarManager fullscreenColor(int color) {
        this.mBuilder.fullscreenColor(color);
        return this;
    }

    public void apply() {
        if (mContext == null) {
            Log.e(TAG, "StatusBarManager error, mContext can not null");
            return;
        }
        mContext.get().onStatusBarConfigChange(mBuilder.build());
        mBuilder.init();
    }
}
