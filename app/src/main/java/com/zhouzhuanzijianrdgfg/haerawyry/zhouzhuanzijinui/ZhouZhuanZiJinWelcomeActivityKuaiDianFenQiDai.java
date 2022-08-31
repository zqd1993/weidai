package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.zhouzhuanzijianrdgfg.haerawyry.R;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginStatusModel;
import com.zhouzhuanzijianrdgfg.haerawyry.mvp.XActivity;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ZhouZhuanZiJinApi;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ApiSubscriber;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.NetError;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.XApi;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.StatusBarUtil;
import com.zhouzhuanzijianrdgfg.haerawyry.router.Router;

import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinwidget.WelcomeDialog;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai extends XActivity {

    private WelcomeDialog welcomeDialog;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    private String vivoUri = "https://haoone.oss-cn-hangzhou.aliyuncs.com/812/ozzzj.json";
    private String oppoUri = "https://wentree.oss-cn-hangzhou.aliyuncs.com/830/vzzzj.json";

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
        welcomeDialog = new WelcomeDialog(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                ZhouZhuanZiJinSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                ZhouZhuanZiJinSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai.this)
                        .to(ZhouZhuanZiJinLoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ZhouZhuanZiJinApi.getZc());
                Router.newIntent(ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai.this)
                        .to(ZhouZhuanZiJinWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ZhouZhuanZiJinApi.getYs());
                Router.newIntent(ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai.this)
                        .to(ZhouZhuanZiJinWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(vivoUri)
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
//                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
//                        .to(WebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                ZhouZhuanZiJinApi.API_BASE_URL = net;
                ZhouZhuanZiJinSharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
                Thread.sleep(1000);
                getGankData();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jumpPage() {
        if (isAgree) {
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai.this)
                        .to(ZhouZhuanZiJinHomePageActivity.class)
                        .launch();
            } else {
                Router.newIntent(ZhouZhuanZiJinWelcomeActivityKuaiDianFenQiDai.this)
                        .to(ZhouZhuanZiJinLoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanZiJinApi.getGankService().getGankData()
                    .compose(XApi.<ZhouZhuanZiJinLoginStatusModel>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinLoginStatusModel>getScheduler())
                    .compose(this.<ZhouZhuanZiJinLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinLoginStatusModel zhouZhuanZiJinLoginStatusModel) {
                            if (zhouZhuanZiJinLoginStatusModel != null) {
                                ZhouZhuanZiJinSharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(zhouZhuanZiJinLoginStatusModel.getIs_agree_check()));
                                ZhouZhuanZiJinSharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(zhouZhuanZiJinLoginStatusModel.getIs_code_register()));
                                jumpPage();
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialog != null) {
            welcomeDialog.dismiss();
            welcomeDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        isAgree = ZhouZhuanZiJinSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_zhou_zhuan_zi_jin;
    }

    @Override
    public Object newP() {
        return null;
    }
}
