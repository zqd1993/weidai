package com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.fvhrnthdfggeinihua.hdfghuerungf.R;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi.OpNewGeiNiHuaDaiKuanHttpApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanPreferencesOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanStatusBarUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewweidgt.OpNewGeiNiHuaDaiKuanStartPageRemindDialog;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpNewGeiNiHuaDaiKuanStartPageActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private OpNewGeiNiHuaDaiKuanStartPageRemindDialog opNewGeiNiHuaDaiKuanStartPageRemindDialog;

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
            }
        }, 500);
    }

    private void showDialog() {
//        Looper.prepare();
        opNewGeiNiHuaDaiKuanStartPageRemindDialog = new OpNewGeiNiHuaDaiKuanStartPageRemindDialog(this);
        opNewGeiNiHuaDaiKuanStartPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    OpNewGeiNiHuaDaiKuanStartPageActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        opNewGeiNiHuaDaiKuanStartPageRemindDialog.setOnListener(new OpNewGeiNiHuaDaiKuanStartPageRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
//                initUm();
                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveBool("isSure", true);
                opNewGeiNiHuaDaiKuanStartPageRemindDialog.dismiss();
                OpNewGeiNiHuaDaiKuanOpenUtil.getValue(OpNewGeiNiHuaDaiKuanStartPageActivity.this, OpNewGeiNiHuaDaiKuanDlActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", OpNewGeiNiHuaDaiKuanHttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                OpNewGeiNiHuaDaiKuanOpenUtil.getValue(OpNewGeiNiHuaDaiKuanStartPageActivity.this, JumpH5ActivityJinRiYouQianHua.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", OpNewGeiNiHuaDaiKuanHttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                OpNewGeiNiHuaDaiKuanOpenUtil.getValue(OpNewGeiNiHuaDaiKuanStartPageActivity.this, JumpH5ActivityJinRiYouQianHua.class, bundle);
            }
        });
        opNewGeiNiHuaDaiKuanStartPageRemindDialog.show();
//        Looper.loop();
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap mktyhjxfghtry(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap eartfhsrtuy(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://ossbj0714.oss-cn-beijing.aliyuncs.com/server7756.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveString("AGREEMENT", net[1]);
                                Thread.sleep(1000);
                                jumpPage();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jumpPage() {
        if (isSure) {
//            initUm();
            if (TextUtils.isEmpty(phone)) {
                OpNewGeiNiHuaDaiKuanOpenUtil.getValue(OpNewGeiNiHuaDaiKuanStartPageActivity.this, OpNewGeiNiHuaDaiKuanDlActivity.class, null, true);
            } else {
                OpNewGeiNiHuaDaiKuanOpenUtil.getValue(OpNewGeiNiHuaDaiKuanStartPageActivity.this, OpNewGeiNiHuaDaiKuanMainActivity.class, null, true);
            }
        } else {
            showDialog();
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap zgeatdfyry(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap ertzdghrstuy(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUm() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        OpNewGeiNiHuaDaiKuanStatusBarUtil.setTransparent(this, false);
        isSure = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getBool("isSure");
        phone = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap ertgdfzxrty(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap bbryfgxhsrtuy(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gei_ni_hua_dai_kuan_op_new_start_page;
    }

    @Override
    public Object newP() {
        return null;
    }
}
