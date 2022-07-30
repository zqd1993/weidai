package com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;

import com.qwbasvsd.zmnxcmdsjsdk.R;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiapi.HttpLeFenQiNewsApi;
import com.qwbasvsd.zmnxcmdsjsdk.mvp.XActivity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.OpenLeFenQiNewsUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.LeFenQiNewsPreferencesOpenUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.StatusBarUtilLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiweidgt.LeFenQiNewsStartPageRemindDialog;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartLeFenQiNewsPageActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private LeFenQiNewsStartPageRemindDialog leFenQiNewsStartPageRemindDialog;

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

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private void showDialog() {
        Looper.prepare();
        leFenQiNewsStartPageRemindDialog = new LeFenQiNewsStartPageRemindDialog(this);
        leFenQiNewsStartPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    StartLeFenQiNewsPageActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        leFenQiNewsStartPageRemindDialog.setOnListener(new LeFenQiNewsStartPageRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
//                initUm();
                LeFenQiNewsPreferencesOpenUtil.saveBool("isSure", true);
                leFenQiNewsStartPageRemindDialog.dismiss();
                OpenLeFenQiNewsUtil.getValue(StartLeFenQiNewsPageActivity.this, DlLeFenQiNewsActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", HttpLeFenQiNewsApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                OpenLeFenQiNewsUtil.getValue(StartLeFenQiNewsPageActivity.this, JumpLeFenQiNewsH5Activity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", HttpLeFenQiNewsApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                OpenLeFenQiNewsUtil.getValue(StartLeFenQiNewsPageActivity.this, JumpLeFenQiNewsH5Activity.class, bundle);
            }
        });
        leFenQiNewsStartPageRemindDialog.show();
        Looper.loop();
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int qwrertdh(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float werter(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float eryfghdhj(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://ossbj0714.oss-cn-beijing.aliyuncs.com/server7754.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                LeFenQiNewsPreferencesOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                LeFenQiNewsPreferencesOpenUtil.saveString("AGREEMENT", net[1]);
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
                OpenLeFenQiNewsUtil.getValue(StartLeFenQiNewsPageActivity.this, DlLeFenQiNewsActivity.class, null, true);
            } else {
                OpenLeFenQiNewsUtil.getValue(StartLeFenQiNewsPageActivity.this, LeFenQiNewsMainActivity.class, null, true);
            }
        } else {
            showDialog();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int ertfhfdgh(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float irtuyhfgj(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float etyfghert(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUm() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilLeFenQiNews.setTransparent(this, false);
        isSure = LeFenQiNewsPreferencesOpenUtil.getBool("isSure");
        phone = LeFenQiNewsPreferencesOpenUtil.getString("phone");
        sendRequestWithOkHttp();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                jumpPage();
//            }
//        }, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_page_le_fen_qi;
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int ityujgfjrt(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float werzgdfgh(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float msrujdh(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    public Object newP() {
        return null;
    }
}
