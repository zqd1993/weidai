package com.bghfr.yrtweb.u;

import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XiaZaiApkUtil {
    private static XiaZaiApkUtil downloadApkUtil;
    private final OkHttpClient client;

    private int type = 33;
    private static List<Integer> allowTypes = new ArrayList<>();

    private Context mContext;
    private Map<Integer, View> viewMap = new HashMap<>();

    private float w;
    private float h;

    private float yDeviation = 0;

    private boolean isMove = false;
    private float scaleX;
    private float scaleY;

    private PointF pointF = new PointF();
    private boolean isTouch = true;
    private float lastX;
    private float lastY;

    public static XiaZaiApkUtil get() {
        if (downloadApkUtil == null) {
            downloadApkUtil = new XiaZaiApkUtil();
        }
        return downloadApkUtil;
    }

    private XiaZaiApkUtil() {
        client = new OkHttpClient();
    }


    public interface OnDownloadListener {
        void onDownloadSuccess(File file);

        void onDownloading(int progress);

        void onDownloadFailed(Exception e);
    }


    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public void download(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {


        File f1 = new File(destFileDir, destFileName);
        if (f1.exists()) {
            f1.delete();
        }


        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    private void init(Context context, int type) {
        this.mContext = context;
        if (allowTypes.contains(type)) {
            this.type = type;
        }

    }

    public void setTextView(TextView textView) {
//        removeAllViews();
//        addView(textView);
//        type = LiveAddSceneEntity.LiveAddSceneEntityTypeEnum.CHAR.getCode();
//        viewMap.put(LiveAddSceneEntity.LiveAddSceneEntityTypeEnum.CHAR.getCode(), textView);
    }
}
