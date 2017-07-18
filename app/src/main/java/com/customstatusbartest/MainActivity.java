package com.customstatusbartest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fullscreenButton, redButton, alphaButton;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                StatusBarManager.
                        with(this).
                        fullscreen(false).
                        statusBarColor(Color.RED).
                        apply();
                break;
            case R.id.button2:
                StatusBarManager.
                        with(this).
                        fullscreen(true).
                        fullscreenColor(getResources().getColor(R.color.colorPrimary)).
                        apply();
                break;
            case R.id.button3:
                StatusBarManager.
                        with(this).
                        fullscreen(true).
                        apply();
                break;
        }
    }
}
