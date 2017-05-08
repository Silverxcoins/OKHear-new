package com.example.sasha.okhear_new;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

@EViewGroup
public class Overlay extends FrameLayout {

    @ColorRes(R.color.colorMainBackgroundTrain)
    int trainColor;

    @ColorRes(R.color.colorMainBackgroundExam)
    int examColor;

    @DimensionPixelOffsetRes(R.dimen.training_margin_end)
    int trainingMarginEnd;

    @DimensionPixelOffsetRes(R.dimen.overlay_top_margin)
    int topMargin;

    @DimensionPixelOffsetRes(R.dimen.overlay_corners_height)
    int cornersHeight;

    @ViewById(R.id.mode_button)
    FrameLayout modeButton;

    @ViewById(R.id.mode_button_icon)
    View modeButtonIcon;

    @ViewById(R.id.mode_button_text)
    TextView modeButtonText;

    @ViewById(R.id.symbols_button)
    Button symbolsButton;

    @ViewById(R.id.mode_button_foreground)
    ImageView modeButtonForeground;

    @ViewById(R.id.words_button)
    Button wordsButton;

    @ViewById(R.id.dynamic_button)
    Button dynamicButton;

    private Mode mode = Mode.TRAINING;
    private boolean isHidden = false;

    public Overlay(Context context) {
        super(context);
    }

    public Overlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Overlay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Click(R.id.mode_button)
    void onModeButtonClicked() {
        mode = (mode == Mode.TRAINING) ? Mode.EXAM : Mode.TRAINING;
        startRotationAnimation();
        startColorAnimation();
    }

    @Click(R.id.symbols_button)
    void onSymbolsButtonClicked() {
        startMovingAnimation();
        getMainActivity().showRecyclerView(true, MainActivity.ComplexityMode.SYMBOLS);
    }

    @Click(R.id.words_button)
    void onWordsButtonClicked() {
        startMovingAnimation();
        getMainActivity().showRecyclerView(true, MainActivity.ComplexityMode.WORDS);
    }

    @Click(R.id.dynamic_button)
    void onDynamicButtonClicked() {
        startMovingAnimation();
    }

    private void startRotationAnimation() {
        modeButton.setEnabled(false);
        ValueAnimator rotationAnimator = ValueAnimator.ofFloat(0, 180);
        rotationAnimator.setDuration(400);
        rotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                if (value <= 90) {
                    modeButton.setRotationY(value);
                } else {
                    modeButton.setRotationY(180 - value);
                    modeButtonIcon.setBackgroundResource(mode == Mode.TRAINING ? R.drawable.ic_training : R.drawable.ic_exam);
                    modeButtonText.setText(mode == Mode.TRAINING ? R.string.training_string : R.string.exam_string);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) modeButtonIcon.getLayoutParams();
                    int marginEnd = (mode == Mode.TRAINING) ? trainingMarginEnd : 0;
                    params.setMargins(0, 0, marginEnd, 0);
                    modeButtonIcon.setLayoutParams(params);
                    modeButtonIcon.invalidate();
                }
                if (value == 180) {
                    modeButton.setEnabled(true);
                }
            }
        });
        rotationAnimator.start();
    }

    private void startColorAnimation() {
        final int startColor = (mode == Mode.TRAINING) ? examColor : trainColor;
        final int endColor = (mode == Mode.TRAINING) ? trainColor : examColor;
        ValueAnimator colorAnimator = ValueAnimator.ofArgb(startColor, endColor);
        colorAnimator.setDuration(400);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int color = (Integer) valueAnimator.getAnimatedValue();
                setMainColor(color);
            }
        });
        colorAnimator.start();
    }

    public void startMovingAnimation() {
        final int startValue = isHidden ? getBottom() - cornersHeight : topMargin;
        final int endValue = isHidden ? topMargin : getBottom() - cornersHeight;
        ValueAnimator movingAnimator = ValueAnimator.ofInt(startValue, endValue);
        movingAnimator.setDuration(300);
        enableLevelsButtons(false);
        movingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setY(value);
                if (value == endValue) {
                    if (!isHidden) {
                        getMainActivity().showUpButton(true);
                    } else {
                        enableLevelsButtons(true);
                    }
                    isHidden = !isHidden;
                }
            }
        });
        movingAnimator.start();
    }

    private void setMainColor(int color) {
        symbolsButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
        wordsButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
        dynamicButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
        modeButtonForeground.setColorFilter(color);
        modeButtonIcon.getBackground().setTint(color);
        modeButtonText.setTextColor(color);
        getMainActivity().setBackgroundColor(color);
    }

    private void enableLevelsButtons(boolean enable) {
        symbolsButton.setEnabled(enable);
        wordsButton.setEnabled(enable);
        dynamicButton.setEnabled(enable);
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getContext();
    }

    private enum Mode {
        TRAINING,
        EXAM
    }
}
