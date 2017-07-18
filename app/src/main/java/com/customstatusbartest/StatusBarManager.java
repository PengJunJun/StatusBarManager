package com.customstatusbartest;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by pjj on 2017/6/14.
 * <p>
 * status bar manager,
 */

public class StatusBarManager implements IStatusBarCallback {
    private static final String TAG = "StatusBarManager";
    private StatusBarConfig.Builder mBuilder;
    private WeakReference<Activity> mContext;
    private IStatusBarStrategy mStatusBarStrategy;

    public StatusBarManager(Activity context) {
        this.mContext = new WeakReference<>(context);
        this.mBuilder = new StatusBarConfig.Builder();
    }

    public static StatusBarManager with(Activity context) {
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
        onStatusBarConfigChange(mBuilder.build());
        mBuilder.reset();
    }

    @Override
    public void onStatusBarConfigChange(StatusBarConfig config) {
        if (config == null) {
            return;
        }
        if (mStatusBarStrategy == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mStatusBarStrategy = new LollipopStatusBarStrategy(mContext);
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mStatusBarStrategy = new KitkatStatusBarStrategy(mContext);
            } else {
                return;
            }
        }
        if (config.isFullscreen()) {
            if (config.getFullscreenColor() != -1) {
                mStatusBarStrategy.fullscreenStatusBarColor(config.getFullscreenColor());
            } else {
                mStatusBarStrategy.translucentStatusBar();
            }
            return;
        }
        mStatusBarStrategy.setStatusBarColor(config.getStatusBarColor());
    }
}
