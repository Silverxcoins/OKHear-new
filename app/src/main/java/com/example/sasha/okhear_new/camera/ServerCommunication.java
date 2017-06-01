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

        int bytesSum = symbolBytes.length + terminatorBytes.length + endTerminatorBytes.length;
        for (byte[] bytes : handsBytes) {
            bytesSum += bytes.length;
            if (handsBytes.indexOf(bytes) != handsBytes.size() - 1) {
                bytesSum += terminatorBytes.length;
            }
        }

        byte[] result = new byte[bytesSum];
        int i = 0;

        for (byte b : symbolBytes) {
            result[i++] = b;
        }
        for (byte b : terminatorBytes) {
            result[i++] = b;
        }
        for (byte[] bytes : handsBytes) {
            for (byte b : bytes) {
                result[i++] = b;
            }
            if (handsBytes.indexOf(bytes) != handsBytes.size() - 1) {
                for (byte b : terminatorBytes) {
                    result[i++] = b;
                }
            }
        }
        for (byte b : endTerminatorBytes) {
            result[i++] = b;
        }

        try {
            Socket socket = new Socket("62.109.1.48", 6000);

            try(InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()) {

                out.write(result);
                out.flush();

                final byte[] responseBytes = new byte[1024 * 32];
                final int readBytes = in.read(responseBytes);

//                JSONObject json = new JSONObject(new String(responseBytes, 0, readBytes));
//                JSONArray array = json.getJSONArray("gesture");
//                StringBuilder builder = new StringBuilder();
//                builder.append("0: " + array.get(0))
//                        .append("\n 1: " + array.get(1))
//                        .append("\n 2: " + array.get(2))
//                        .append("\n 3: " + array.get(3))
//                        .append("\n 4: " + array.get(4))
//                        .append("\n 5: " + array.get(5))
//                        .append("\n 6: " + array.get(6))
//                        .append("\n 7: " + array.get(7))
//                        .append("\n 8: " + array.get(8))
//                        .append("\n 9: " + array.get(9))
//                        .append("\n A: " + array.get(10))
//                        .append("\n Б: " + array.get(11))
//                        .append("\n В: " + array.get(12))
//                        .append("\n Г: " + array.get(13))
//                        .append("\n Д: " + array.get(14))
//                        .append("\n Е: " + array.get(15))
//                        .append("\n Ж: " + array.get(16))
//                        .append("\n З: " + array.get(17))
//                        .append("\n И: " + array.get(18))
//                        .append("\n Й: " + array.get(19));

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
