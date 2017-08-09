package com.customstatusbartest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fullscreenButton, redButton, alphaButton;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        redButton = (Button) findViewById(R.id.button1);
        fullscreenButton = (Button) findViewById(R.id.button2);
        alphaButton = (Button) findViewById(R.id.button3);
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolBar);
        redButton.setOnClickListener(this);
        fullscreenButton.setOnClickListener(this);
        alphaButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                EventBus.getDefault().post("hello first1");
                StatusBarManager.
                        with(this).
                        fullscreen(false).
                        statusBarColor(Color.RED).
                        apply();
                break;
            case R.id.button2:
                EventBus.getDefault().post("hello first2");
                StatusBarManager.
                        with(this).
                        fullscreen(true).
                        fullscreenColor(getResources().getColor(R.color.colorPrimary)).
                        apply();
                break;
            case R.id.button3:
                EventBus.getDefault().post("hello first3");
                StatusBarManager.
                        with(this).
                        fullscreen(true).
                        apply();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetStatusBarMessage(String message) {
        Log.e("MainActivity1", message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(String message) {
        Log.e("MainActivity2", message);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
