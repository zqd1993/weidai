package com.dfgderv.erterqweq.widget;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownUtil {
    private final OkHttpClient okHttpClient;
    private static DownUtil downUtil;

    /**
     * 判断是否MediaPlayer是否释放的标志
     */
    private boolean isRelease = true;


    /**
     * 是否循环播放
     */
    private boolean isLooping = true;

    private String proxyUrl;

    private Context mContext;

    private MediaPlayer mMediaPlayer;

    private int currentPosition = 0;
    private String lastMusicUrl;


    private int musicPosition;

    private CompositeDisposable disposables;

    private String fileDir;
    private boolean isExist;

    public static DownUtil get() {
        if (downUtil == null) {
            downUtil = new DownUtil();
        }
        return downUtil;
    }

    private DownUtil() {
        okHttpClient = new OkHttpClient();
    }

    public void download(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {

        File f1 = new File(destFileDir, destFileName);
        if (f1.exists()) {
            f1.delete();
        }

        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
                        // 下载中更新进度条
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
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

    public interface OnDownloadListener {
        void onDownloadSuccess(File file);
        void onDownloading(int progress);
        void onDownloadFailed(Exception e);
    }
}
