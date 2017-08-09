package com.customstatusbartest;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by bykj003 on 2017/7/18.
 */

public class StatusBarFactory {
    private static volatile StatusBarFactory mStatusBarFactory;
    private ConcurrentHashMap<WeakReference<Activity>, StatusBarManager> mStatusBarHashMap = new ConcurrentHashMap<>();

    private StatusBarFactory(){}

    public static StatusBarFactory getInstance() {
        if (mStatusBarFactory == null) {
            synchronized (StatusBarFactory.class) {
                if (mStatusBarFactory == null) {
                    mStatusBarFactory = new StatusBarFactory();
                }
            }
        }
        return mStatusBarFactory;
    }

    public StatusBarManager create(Activity context) {
        WeakReference<Activity> reference = new WeakReference<>(context);
        StatusBarManager statusBarManager = containsKey(reference);
        if (statusBarManager == null) {
            statusBarManager = new StatusBarManager(context);
            mStatusBarHashMap.put(reference, statusBarManager);
            Log.e("StatusBarFactory", "from new reference");
        } else {
            Log.e("StatusBarFactory", "from map");
        }
        return statusBarManager;
    }

    private StatusBarManager containsKey(WeakReference<Activity> reference) {
        if (mStatusBarHashMap.size() == 0
                || reference == null
                || reference.get() == null) {
            return null;
        }
        Set<Map.Entry<WeakReference<Activity>, StatusBarManager>> entries = mStatusBarHashMap.entrySet();
        Iterator<Map.Entry<WeakReference<Activity>, StatusBarManager>> iterator = entries.iterator();
        Map.Entry<WeakReference<Activity>, StatusBarManager> element;
        while (iterator.hasNext()) {
            element = iterator.next();
            if (element != null && element.getKey().get() == reference.get()) {
                return element.getValue();
            }
        }
        return null;
    }
}
