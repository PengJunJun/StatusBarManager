package com.customstatusbartest;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

/**
 * Created by pjj on 2017/6/15.
 */

public interface IStatusBarStrategy {
    void translucentStatusBar();

    void setStatusBarColor(@ColorInt int color);

    void fullscreenStatusBarColor(@ColorInt int color);
}
