package com.customstatusbartest;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by pjj on 2017/6/13.
 * <p>
 * config status bar, include status bar color and context,
 */

public class StatusBarConfig {

    private int mStatusBarColor = -1;
    private boolean mIsFullscreen;
    private int mFullscreenColor = -1;

    public StatusBarConfig(int statusBarColor) {
        this.mStatusBarColor = statusBarColor;
    }

    public StatusBarConfig(int statusBarColor, boolean isFullscreen) {
        this.mStatusBarColor = statusBarColor;
        this.mIsFullscreen = isFullscreen;
    }

    public StatusBarConfig(int mStatusBarColor, boolean mIsFullscreen, int mFullscreenColor) {
        this.mStatusBarColor = mStatusBarColor;
        this.mIsFullscreen = mIsFullscreen;
        this.mFullscreenColor = mFullscreenColor;
    }

    public int getStatusBarColor() {
        return mStatusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.mStatusBarColor = statusBarColor;
    }

    public boolean isFullscreen() {
        return mIsFullscreen;
    }

    public void setIsFullscreen(boolean isFullscreen) {
        this.mIsFullscreen = isFullscreen;
    }

    public int getFullscreenColor() {
        return mFullscreenColor;
    }

    public void setFullscreenColor(int mFullscreenColor) {
        this.mFullscreenColor = mFullscreenColor;
    }

    public static class Builder {
        private WeakReference<Context> mContext;
        private int mStatusBarColor = -1;
        private boolean mIsFullscreen;
        private int mFullscreenColor = -1;

        public Builder statusBarColor(int color) {
            this.mStatusBarColor = color;
            return this;
        }

        public Builder context(Context context) {
            this.mContext = new WeakReference<Context>(context);
            return this;
        }

        public Builder fullscreen(boolean fullscreen) {
            this.mIsFullscreen = fullscreen;
            return this;
        }

        public Builder fullscreenColor(int color) {
            this.mFullscreenColor = color;
            return this;
        }

        public StatusBarConfig build() {
            return new StatusBarConfig(mStatusBarColor, mIsFullscreen, mFullscreenColor);
        }
    }
}
