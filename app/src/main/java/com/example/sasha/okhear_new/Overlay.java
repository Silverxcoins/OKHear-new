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

    Mode mode = Mode.TRAINING;

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

    private void startRotationAnimation() {
        modeButton.setEnabled(false);
        ValueAnimator rotationAnimator = ValueAnimator.ofFloat(0, 180);
        rotationAnimator.setDuration(500);
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
        int startColor = (mode == Mode.TRAINING) ? examColor : trainColor;
        int endColor = (mode == Mode.TRAINING) ? trainColor : examColor;
        ValueAnimator colorAnimator = ValueAnimator.ofArgb(startColor, endColor);
        colorAnimator.setDuration(500);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int color = (Integer) valueAnimator.getAnimatedValue();
                setMainColor(color);
            }
        });
        colorAnimator.start();
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

    private MainActivity getMainActivity() {
        return (MainActivity) getContext();
    }

    private enum Mode {
        TRAINING,
        EXAM
    }
}
