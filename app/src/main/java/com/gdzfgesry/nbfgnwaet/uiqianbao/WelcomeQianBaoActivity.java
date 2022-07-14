package com.gdzfgesry.nbfgnwaet.uiqianbao;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.gdzfgesry.nbfgnwaet.R;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.LoginStatusQianBaoModel;
import com.gdzfgesry.nbfgnwaet.mvp.XActivity;
import com.gdzfgesry.nbfgnwaet.netqianbao.ApiSubscriber;
import com.gdzfgesry.nbfgnwaet.netqianbao.NetError;
import com.gdzfgesry.nbfgnwaet.netqianbao.XApi;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.SharedQianBaoPreferencesUtilis;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.QianBaoStatusQianBaoBarUtil;
import com.gdzfgesry.nbfgnwaet.router.Router;

import com.gdzfgesry.nbfgnwaet.netqianbao.QianBaoApi;
import com.gdzfgesry.nbfgnwaet.qianbaowidget.WelcomeDialogQianBao;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeQianBaoActivity extends XActivity {

    private WelcomeDialogQianBao welcomeDialogQianBao;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
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
        welcomeDialogQianBao = new WelcomeDialogQianBao(this, "温馨提示");
        welcomeDialogQianBao.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeQianBaoActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogQianBao.setOnClickedListener(new WelcomeDialogQianBao.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedQianBaoPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SharedQianBaoPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeQianBaoActivity.this)
                        .to(LoginQianBaoActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeQianBaoActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", QianBaoApi.getZc());
                Router.newIntent(WelcomeQianBaoActivity.this)
                        .to(QianBaoWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", QianBaoApi.getYs());
                Router.newIntent(WelcomeQianBaoActivity.this)
                        .to(QianBaoWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogQianBao.show();
    }

    public static String zrdyfgh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double kdtyixghdfh(Object o) {

        return toDouble(o, 0);
    }

    public static double wthxfgh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long jhjertdfh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/k-yjqb.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            String net = jsonObject.getString("data");//取得其名字的值，一般是字符串
            if (!TextUtils.isEmpty(net)) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("tag", 1);
//                bundle.putString("url", net);
//                Router.newIntent(WelcomeActivity.this)
//                        .to(WebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                QianBaoApi.API_BASE_URL = net;
                SharedQianBaoPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
                Thread.sleep(1000);
                getGankData();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String zfgryeryhdfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mzxhfgx(Object o) {

        return toDouble(o, 0);
    }

    public static double ewtxdfhx(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long mxftyhzhfh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private void jumpPage() {
        if (isAgree) {
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(WelcomeQianBaoActivity.this)
                        .to(HomePageActivityQianBao.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeQianBaoActivity.this)
                        .to(LoginQianBaoActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            QianBaoApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusQianBaoModel>getApiTransformer())
                    .compose(XApi.<LoginStatusQianBaoModel>getScheduler())
                    .compose(this.<LoginStatusQianBaoModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusQianBaoModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusQianBaoModel loginStatusQianBaoModel) {
                            if (loginStatusQianBaoModel != null) {
                                SharedQianBaoPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusQianBaoModel.getIs_agree_check()));
                                SharedQianBaoPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginStatusQianBaoModel.getIs_code_register()));
                                jumpPage();
                            }
                        }
                    });
        }
    }

    public static String nxfhyzer(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mghjkxrtyzdf(Object o) {

        return toDouble(o, 0);
    }

    public static double qwrdhxfg(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long uregfgj(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialogQianBao != null) {
            welcomeDialogQianBao.dismiss();
            welcomeDialogQianBao = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        QianBaoStatusQianBaoBarUtil.setTransparent(this, false);
        isAgree = SharedQianBaoPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SharedQianBaoPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    public static String herhnzx(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double earyjtgdf(Object o) {

        return toDouble(o, 0);
    }

    public static double wetrhxfgh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long msrtudzfh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qian_bao_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
