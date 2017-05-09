package com.example.sasha.okhear_new.camera;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sasha.okhear_new.R;
import com.example.sasha.okhear_new.utils.StatusBarUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

@EViewGroup
public class CameraScreen extends FrameLayout implements ServerCommunication.Callback, FrameManager.FrameProcessingListener {

    @ViewById(R.id.camera_view)
    SurfaceView cameraView;

    @ViewById(R.id.iv)
    ImageView iv;

    @ViewById(R.id.hint)
    ImageView hint;

    @ViewById(R.id.text)
    TextView text;

    @ViewById(R.id.detected_rectangles)
    DetectedRectangles detectedRectangles;

    @DrawableRes(R.drawable.be)
    Drawable be;

    @Bean
    ServerCommunication imagesServerCommunication;

    @Bean
    FrameManager frameManager;

    Timer timeoutTimer;

    private static final String TAG = "CameraScreen";

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private int cameraId;

    private boolean isHidden = true;

    private final ThreadLocal<File> cascadeFile = new ThreadLocal<>();
    private CascadeClassifier javaDetector;

    AtomicBoolean readyToSend = new AtomicBoolean(true);

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getContext()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
//                    System.loadLibrary("detection_based_tracker");
                    try {
                        initDetector();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }
                } break;
                default: {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public CameraScreen(Context context) {
        super(context);
    }

    public CameraScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    public void init() {
        surfaceHolder = cameraView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                if (camera == null) {
                    camera = Camera.open();
                }
                cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                try {
                    camera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Camera.Size previewSize = camera.getParameters().getPreviewSize();
                float aspect = (float) previewSize.width / previewSize.height;
                int previewSurfaceHeight = cameraView.getHeight();
                camera.setDisplayOrientation(90);
                cameraView.getLayoutParams().height = previewSurfaceHeight;
                cameraView.getLayoutParams().width = (int) (previewSurfaceHeight / aspect);

                Camera.Parameters params = camera.getParameters();
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                camera.setParameters(params);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (camera != null) {
                    camera.setPreviewCallback(null);
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Click(R.id.back_button)
    void onBackClicked() {
        hideCamera();
    }

    @Click(R.id.swap_button)
    void onSwapClicked() {
        swapCamera();
    }

    private void startSendingFrames(boolean start) {
        if (start) {
            frameManager.setFrameProcessingListener(this);
            imagesServerCommunication.setCallback(this);
            readyToSend.set(true);
            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, final Camera camera) {
                    if (readyToSend.get()) {
                        readyToSend.set(false);
                        frameManager.detectHand(bytes, camera, javaDetector, isFrontCamera());
                        timeoutTimer = new Timer();
                        timeoutTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                readyToSend.set(true);
                            }
                        }, 3000);
                    }
                }
            });
        } else {
            frameManager.setFrameProcessingListener(null);
            camera.setOneShotPreviewCallback(null);
            imagesServerCommunication.setCallback(null);
        }
    }

    @UiThread
    @Override
    public void onHandBitmapCreated(FrameManager.BitmapWithCoords bitmapWithCoords,
                                    Rect[] rects) {
        Rect croppedRect = new Rect(
                (int) bitmapWithCoords.getX(),
                (int) bitmapWithCoords.getY(),
                (int) bitmapWithCoords.getWidth(),
                (int) bitmapWithCoords.getHeight()
        );
//        detectedRectangles.setRectangles(rects, croppedRect);

        iv.setImageBitmap(bitmapWithCoords.getBitmap());
    }

    @UiThread
    @Override
    public void onHandBytesReady(byte[] bytes) {
        if (bytes != null) {
            imagesServerCommunication.sendToServerWithSocket(bytes);
        }
    }

    @UiThread
    @Override
    public void onResponse(String response) {
        Log.d(TAG, "onResponse: " + response);
        try {
            if (timeoutTimer != null) {
                timeoutTimer.cancel();
                timeoutTimer = null;
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    readyToSend.set(true);
                }
            }, 400);
            JSONObject json = new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideCamera() {
        isHidden = true;
        startMovingAnimation();
        startSendingFrames(false);
    }

    public void showCamera(String text, boolean withHint) {
        isHidden = false;
        StatusBarUtil.hideStatusBar(getContext());
        setVisibility(VISIBLE);
        setX(0);
        hint.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.be));
        startScaleAnimation();
        camera.startPreview();
        this.text.setText(text);
        startSendingFrames(true);
    }

    private void startMovingAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, getRight());
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setX(value);
                if (value == getRight()) {
                    camera.stopPreview();
                    StatusBarUtil.hideStatusBar(getContext());
                }
            }
        });
        animator.start();
    }

    private void startScaleAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.4f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                hint.setScaleX(value);
                hint.setScaleY(value);
                hint.setX(0);
                hint.setY(0);
            }
        });
        animator.start();
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void swapCamera() {
        camera.stopPreview();
        camera.release();

        if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else {
            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        camera = Camera.open(cameraId);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, getContext(), mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initDetector() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.cascade_right_letters);
        File cascadeDir = getContext().getDir("cascade", Context.MODE_PRIVATE);
        cascadeFile.set(new File(cascadeDir, "cascade_right_letters.xml"));
        FileOutputStream os = new FileOutputStream(cascadeFile.get());
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        is.close();
        os.close();
        javaDetector = new CascadeClassifier(cascadeFile.get().getAbsolutePath());
        javaDetector.load(cascadeFile.get().getAbsolutePath());
        if (javaDetector.empty()) {
            Log.e(TAG, "Failed to load cascade classifier");
            javaDetector = null;
        } else {
            Log.i(TAG, "Loaded cascade classifier from " + cascadeFile.get().getAbsolutePath());
        }
        cascadeDir.delete();
    }

    private boolean isFrontCamera() {
        return cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }
}
