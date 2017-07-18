package com.customstatusbartest;

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
    private ConcurrentHashMap<WeakReference<IStatusBarCallback>, StatusBarManager> mStatusBarHashMap = new ConcurrentHashMap<>();

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

    public StatusBarManager create(IStatusBarCallback context) {
        WeakReference<IStatusBarCallback> reference = new WeakReference<>(context);
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

    private StatusBarManager containsKey(WeakReference<IStatusBarCallback> reference) {
        if (mStatusBarHashMap.size() == 0
                || reference == null
                || reference.get() == null) {
            return null;
        }
        Set<Map.Entry<WeakReference<IStatusBarCallback>, StatusBarManager>> entries = mStatusBarHashMap.entrySet();
        Iterator<Map.Entry<WeakReference<IStatusBarCallback>, StatusBarManager>> iterator = entries.iterator();
        Map.Entry<WeakReference<IStatusBarCallback>, StatusBarManager> element;
        while (iterator.hasNext()) {
            element = iterator.next();
            if (element != null && element.getKey().get() == reference.get()) {
                return element.getValue();
            }
        }
        return null;
    }
}
