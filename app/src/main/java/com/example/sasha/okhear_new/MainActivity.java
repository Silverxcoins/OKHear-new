package com.example.sasha.okhear_new;

import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.sasha.okhear_new.utils.StatusBarUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.main_layout)
    FrameLayout mainLayout;

    @AfterViews
    void init() {
        StatusBarUtil.setupFullscreenActivity(this);
    }

    public void setBackgroundColor(int color) {
        mainLayout.setBackgroundColor(color);
    }
}
