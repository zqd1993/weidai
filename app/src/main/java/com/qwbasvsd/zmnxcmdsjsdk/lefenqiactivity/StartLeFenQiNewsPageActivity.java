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
import com.umeng.commonsdk.UMConfigure;

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
    public static int sp2px(Context context, float spVal)
    {
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
    public static float px2dp(Context context, float pxVal)
    {
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
    public static float px2sp(Context context, float pxVal)
    {
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
                if (!TextUtils.isEmpty(LeFenQiNewsPreferencesOpenUtil.getString("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putString("url", LeFenQiNewsPreferencesOpenUtil.getString("AGREEMENT") + HttpLeFenQiNewsApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenLeFenQiNewsUtil.getValue(StartLeFenQiNewsPageActivity.this, JumpLeFenQiNewsH5Activity.class, bundle);
                }
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                if (!TextUtils.isEmpty(LeFenQiNewsPreferencesOpenUtil.getString("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putString("url", LeFenQiNewsPreferencesOpenUtil.getString("AGREEMENT") + HttpLeFenQiNewsApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenLeFenQiNewsUtil.getValue(StartLeFenQiNewsPageActivity.this, JumpLeFenQiNewsH5Activity.class, bundle);
                }
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
    public static int qwrertdh(Context context, float spVal)
    {
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
    public static float werter(Context context, float pxVal)
    {
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
    public static float eryfghdhj(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7733.txt")
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
    public static int ertfhfdgh(Context context, float spVal)
    {
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
    public static float irtuyhfgj(Context context, float pxVal)
    {
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
    public static float etyfghert(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUm() {
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (!UMConfigure.isInit) {
            UMConfigure.setLogEnabled(true);
            Log.d("youmeng", "zhuche chenggong");
            //友盟正式初始化
//            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
            // 参数一：当前上下文context；
            // 参数二：应用申请的Appkey（需替换）；
            // 参数三：渠道名称；
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
            UMConfigure.init(this, "629eff2005844627b5a41d7f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
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
    public static int ityujgfjrt(Context context, float spVal)
    {
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
    public static float werzgdfgh(Context context, float pxVal)
    {
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
    public static float msrujdh(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    public Object newP() {
        return null;
    }
}
