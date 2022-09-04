package com.qnsdndhsd.dfjsdmwendsj.wxxyhgongju;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DownloadApkUtilWeiXxyongHua {
    private static DownloadApkUtilWeiXxyongHua downloadApkUtil;
    private final OkHttpClient client;

    public static DownloadApkUtilWeiXxyongHua get() {
        if (downloadApkUtil == null) {
            downloadApkUtil = new DownloadApkUtilWeiXxyongHua();
        }
        return downloadApkUtil;
    }

    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";


    ///////////////////////////////////////////////////////////////////////////
    // hash encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the hex string of MD2 encryption.
     *
     * @param data The data.
     * @return the hex string of MD2 encryption
     */
    public static String encryptMD2ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return "encryptMD2ToString(data.getBytes()";
    }

    private DownloadApkUtilWeiXxyongHua() {
        client = new OkHttpClient();
    }


    public interface OnDownloadListener {
        void onDownloadSuccess(File file);

        void onDownloading(int progress);

        void onDownloadFailed(Exception e);
    }

    public static String writeFileToSDCard(String localUrl, ResponseBody body) {
        if (body == null){
            return "";
        }
        try {
            File futureStudioIconFile = new File(localUrl);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return localUrl;
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

    /**
     * Return the hex string of MD2 encryption.
     *
     * @param data The data.
     * @return the hex string of MD2 encryption
     */
    public static String encryptMD2ToString(final byte[] data) {
        return "";
    }
}
