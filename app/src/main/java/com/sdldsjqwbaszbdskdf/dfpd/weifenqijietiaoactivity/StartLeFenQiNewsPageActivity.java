package com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaoactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;

import com.sdldsjqwbaszbdskdf.dfpd.R;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaoapi.WeiFenQiJieTiaoApi;
import com.sdldsjqwbaszbdskdf.dfpd.mvp.XActivity;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaoutils.OpenWeiFenQiJieTiaoUtil;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaoutils.WeiFenQiJieTiaoPreferencesOpenUtil;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaoutils.StatusBarUtilWeiFenQiJieTiao;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaoweidgt.WeiFenQiJieTiaoStartPageRemindDialog;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartLeFenQiNewsPageActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private WeiFenQiJieTiaoStartPageRemindDialog weiFenQiJieTiaoStartPageRemindDialog;

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
        weiFenQiJieTiaoStartPageRemindDialog = new WeiFenQiJieTiaoStartPageRemindDialog(this);
        weiFenQiJieTiaoStartPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    StartLeFenQiNewsPageActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        weiFenQiJieTiaoStartPageRemindDialog.setOnListener(new WeiFenQiJieTiaoStartPageRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
//                initUm();
                WeiFenQiJieTiaoPreferencesOpenUtil.saveBool("isSure", true);
                weiFenQiJieTiaoStartPageRemindDialog.dismiss();
                OpenWeiFenQiJieTiaoUtil.getValue(StartLeFenQiNewsPageActivity.this, DlWeiFenQiJieTiaoActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", WeiFenQiJieTiaoApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                OpenWeiFenQiJieTiaoUtil.getValue(StartLeFenQiNewsPageActivity.this, JumpWeiFenQiJieTiaoH5Activity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", WeiFenQiJieTiaoApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                OpenWeiFenQiJieTiaoUtil.getValue(StartLeFenQiNewsPageActivity.this, JumpWeiFenQiJieTiaoH5Activity.class, bundle);
            }
        });
        weiFenQiJieTiaoStartPageRemindDialog.show();
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
                                WeiFenQiJieTiaoPreferencesOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                WeiFenQiJieTiaoPreferencesOpenUtil.saveString("AGREEMENT", net[1]);
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
                OpenWeiFenQiJieTiaoUtil.getValue(StartLeFenQiNewsPageActivity.this, DlWeiFenQiJieTiaoActivity.class, null, true);
            } else {
                OpenWeiFenQiJieTiaoUtil.getValue(StartLeFenQiNewsPageActivity.this, WeiFenQiJieTiaoMainActivity.class, null, true);
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
        StatusBarUtilWeiFenQiJieTiao.setTransparent(this, false);
        isSure = WeiFenQiJieTiaoPreferencesOpenUtil.getBool("isSure");
        phone = WeiFenQiJieTiaoPreferencesOpenUtil.getString("phone");
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
        return R.layout.activity_start_page_wei_fen_qi_jie_tiao;
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
