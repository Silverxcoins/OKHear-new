package com.example.sasha.okhear_new;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sasha.okhear_new.camera.CameraScreen;
import com.example.sasha.okhear_new.levels_recycler_view.DataSource;
import com.example.sasha.okhear_new.levels_recycler_view.LevelsAdapter;
import com.example.sasha.okhear_new.levels_recycler_view.SymbolsDataSource;
import com.example.sasha.okhear_new.levels_recycler_view.WordsDataSource;
import com.example.sasha.okhear_new.utils.StatusBarUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @DimensionPixelOffsetRes(R.dimen.up_button_top_position)
    int upButtonPosition;

    @ViewById(R.id.main_layout)
    FrameLayout mainLayout;

    @ViewById(R.id.up_button)
    ImageView upButton;

    @ViewById(R.id.overlay)
    Overlay overlay;

    @ViewById(R.id.list)
    RecyclerView recyclerView;

    @ViewById(R.id.camera_screen)
    CameraScreen cameraScreen;

    @ViewById(R.id.transparent_view)
    View transparentView;

    DataSource symbolsDataSource = new SymbolsDataSource();
    DataSource wordsDataSource = new WordsDataSource();

    @AfterViews
    void init() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            cameraScreen.init(false);
        }
        StatusBarUtil.setupFullscreenActivity(this);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraScreen.init(true);
            } else {
                finish();
            }
        }
    }

    public void showRecyclerView(boolean show, ComplexityMode complexityMode) {
        if (show) {
            DataSource dataSource = null;
            switch (complexityMode) {
                case SYMBOLS:
                    dataSource = symbolsDataSource;
                    break;
                case WORDS:
                case DYNAMIC:
                    dataSource = wordsDataSource;
                    break;
            }
            GridLayoutManager manager = new GridLayoutManager(this, 3);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == recyclerView.getAdapter().getItemCount() - 1) {
                        return 3;
                    }
                    return 1;
                }
            });
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new LevelsAdapter(recyclerView, dataSource, cameraScreen, overlay.isTraining()));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void setBackgroundColor(int color) {
        mainLayout.setBackgroundColor(color);
    }

    @Click(R.id.up_button)
    void onUpButtonClicked() {
        showUpButton(false);
        showRecyclerView(false, null);
    }

    public void showUpButton(final boolean show) {
        int bottom = mainLayout.getBottom();
        final int startValue = show ? bottom : bottom - upButtonPosition;
        final int endValue = show ? bottom - upButtonPosition : bottom;
        ValueAnimator animator = ValueAnimator.ofInt(startValue, endValue);
        animator.setDuration(100);
        upButton.setEnabled(false);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                upButton.setY(value);
                if (value == endValue) {
                    if (!show) {
                        overlay.startMovingAnimation();
                    }
                    upButton.setEnabled(true);
                }
            }
        });
        animator.start();
    }

    @Override
    public void onBackPressed() {
        if (!cameraScreen.isHidden()) {
            cameraScreen.hideCamera();
        } else if (overlay.isHidden()) {
            showUpButton(false);
        } else {
            super.onBackPressed();
        }
    }

    public void enableClick(boolean enable) {
        transparentView.setVisibility(enable ? View.INVISIBLE : View.VISIBLE);
    }

    public enum ComplexityMode {
        SYMBOLS,
        WORDS,
        DYNAMIC
    }
}
