package com.example.sasha.okhear_new;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
public class MainActivity extends AppCompatActivity {

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

    DataSource symbolsDataSource = new SymbolsDataSource();
    DataSource wordsDataSource = new WordsDataSource();

    @AfterViews
    void init() {
        StatusBarUtil.setupFullscreenActivity(this);
    }

    public void showRecyclerView(boolean show, ComplexityMode complexityMode) {
        if (!show) {
            startTransparencyAnimation(false);
        } else {
            startTransparencyAnimation(true);
            recyclerView.setVisibility(View.VISIBLE);
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
            recyclerView.setAdapter(new LevelsAdapter(dataSource));
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

    private void startTransparencyAnimation(boolean show) {
        ValueAnimator animator = ValueAnimator.ofFloat(show ? 0 : 1, show ? 1 : 0);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                recyclerView.setAlpha((float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public enum ComplexityMode {
        SYMBOLS,
        WORDS,
        DYNAMIC
    }
}
