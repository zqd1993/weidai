package com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadMeiJienFenSsdfQiApkUtil {
    private static DownloadMeiJienFenSsdfQiApkUtil downloadMeiJienFenSsdfQiApkUtil;
    private final OkHttpClient client;

    public static DownloadMeiJienFenSsdfQiApkUtil get() {
        if (downloadMeiJienFenSsdfQiApkUtil == null) {
            downloadMeiJienFenSsdfQiApkUtil = new DownloadMeiJienFenSsdfQiApkUtil();
        }
        return downloadMeiJienFenSsdfQiApkUtil;
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void fixInputMethodMemoryLeak(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private DownloadMeiJienFenSsdfQiApkUtil() {
        client = new OkHttpClient();
    }


    public interface OnDownloadListener {
        void onDownloadSuccess(File file);

        void onDownloading(int progress);

        void onDownloadFailed(Exception e);
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void wergzdh(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void ndfhtsrtu(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void wergzdhg(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void bsreyru(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
