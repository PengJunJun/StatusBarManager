package com.customstatusbartest;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

/**
 * Created by pjj on 2017/6/15.
 */

public interface IStatusBarStrategy {
    void translucentStatusBar(boolean fitSystemView);

    void setStatusBarColor(@ColorInt int color, boolean fitSystemView);

    void fullscreenStatusBarColor(@ColorInt int color, boolean fitSystemView);
}
