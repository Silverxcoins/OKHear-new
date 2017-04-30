package com.example.sasha.okhear_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sasha.okhear_new.utils.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setupFullscreenActivity(this);
    }
}
