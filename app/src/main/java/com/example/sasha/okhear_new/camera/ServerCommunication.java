package com.example.sasha.okhear_new.camera;

import android.hardware.Camera;
import android.util.Log;

import com.example.sasha.okhear_new.utils.Utils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EBean(scope = EBean.Scope.Singleton)
public class ServerCommunication {

    public interface Callback {
        void onResponse(String response);
    }

    private final Executor executor = Executors.newSingleThreadExecutor();

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void sendToServer(final Camera camera, final byte[] bytes) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String response = sendToServerInternal(camera, bytes);
                notifyGetResponse(response);
            }
        });
    }

    private String sendToServerInternal(Camera camera, byte[] bytes) {
//        byte[] jpegBytes = Utils.frameBytesToBitmap(camera, bytes);
//        return Http.sendMultiPartPostRequest("", jpegBytes);
        return "";
    }

    @UiThread
    void notifyGetResponse(String response) {
        if (callback != null) {
            callback.onResponse(response);
        }
    }

    public void sendToServerWithSocket(final char symbol, final List<byte[]> handsBytes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendToServerSocketInternal(symbol, handsBytes);
            }
        }).start();
    }

    private void sendToServerSocketInternal(char symbol, final List<byte[]> handsBytes) {
        int pos = Utils.getSymbolPosition(symbol);
        byte[] symbolBytes = String.valueOf(pos).getBytes();
        byte[] terminatorBytes = "\r\r\n".getBytes();
        byte[] endTerminatorBytes = "\n\n\r".getBytes();

//        int bytesSum = symbolBytes.length + terminatorBytes.length + endTerminatorBytes.length;
//        for (byte[] bytes : handsBytes) {
//            bytesSum += bytes.length;
//            if (handsBytes.indexOf(bytes) != handsBytes.size() - 1) {
//                bytesSum += terminatorBytes.length;
//            }
//        }
//
        byte[] result = new byte[handsBytes.get(0).length + terminatorBytes.length];
        int i = 0;
        for (byte b : handsBytes.get(0)) {
            result[i++] = b;
        }
        for (byte b : terminatorBytes) {
            result[i++] = b;
        }
//        int i = 0;
//
//        for (byte b : symbolBytes) {
//            result[i++] = b;
//        }
//        for (byte b : terminatorBytes) {
//            result[i++] = b;
//        }
//        for (byte[] bytes : handsBytes) {
//            for (byte b : bytes) {
//                result[i++] = b;
//            }
//            if (handsBytes.indexOf(bytes) != handsBytes.size() - 1) {
//                for (byte b : terminatorBytes) {
//                    result[i++] = b;
//                }
//            }
//        }
//        for (byte b : endTerminatorBytes) {
//            result[i++] = b;
//        }



        try {
            Socket socket = new Socket("149.154.67.185", 5000);

            try(InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()) {

                out.write(result);
                out.flush();

                final byte[] responseBytes = new byte[1024 * 32];
                final int readBytes = in.read(responseBytes);

                if (callback != null) {
                    try {
//                        Log.d("!!!", new String(responseBytes, 0, readBytes));
                        callback.onResponse(new String(responseBytes, 0, readBytes));
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
