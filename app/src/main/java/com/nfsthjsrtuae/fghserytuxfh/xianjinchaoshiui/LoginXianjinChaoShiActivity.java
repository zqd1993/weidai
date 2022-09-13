package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.XianjinChaoShiApp;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.XianjinChaoShiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.StatusBarUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.ToastUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiwidget.SpanTextVieXianjinChaoShi;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.ActivityCollector;

import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshipresent.LoginXianjinChaoShiPresent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginXianjinChaoShiActivity extends XActivity<LoginXianjinChaoShiPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextVieXianjinChaoShi loginRemindTv;
    @BindView(R.id.remind_cb)
    public CheckBox remindCb;
    @BindView(R.id.rotate_loading)
    public RotateLoading rotateLoading;
    @BindView(R.id.loading_fl)
    public View loadingFl;
    @BindView(R.id.verification_ll)
    public View verificationLl;

    private String phoneStr, verificationStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isNeedChecked = true, isNeedVerification = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilXianjinChaoShi.setTransparent(this, false);
        StatusBarUtilXianjinChaoShi.setLightMode(this);
        isNeedChecked = SharedPreferencesXianjinChaoShiUtilis.getBoolFromPref("is_agree_check");
        isNeedVerification = SharedPreferencesXianjinChaoShiUtilis.getBoolFromPref("is_code_register");
        verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
        remindCb.setChecked(isNeedChecked);
        initListener();
//        ToastUtilKuaiDianFenQiDai.showShort("API_BASE_URL = " + SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + ApiKuaiDianFenQiDai.API_BASE_URL);
        getP().getCompanyInfo();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiXianjinChaoShi.getZc());
                Router.newIntent(LoginXianjinChaoShiActivity.this)
                        .to(UserWebViewActivity.class)
                        .data(bundle)
                        .launch();
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiXianjinChaoShi.getYs());
                Router.newIntent(LoginXianjinChaoShiActivity.this)
                        .to(UserWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://pv.sohu.com/cityjson")
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
            ip = jsonObject.getString("cip");//取得其名字的值，一般是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        loginBtn.setOnClickListener(v -> {
            sendRequestWithOkHttp();
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtilXianjinChaoShi.showShort("请输入手机号");
                return;
            }
            if (!XianjinChaoShiStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilXianjinChaoShi.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilXianjinChaoShi.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtilXianjinChaoShi.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(XianjinChaoShiApp.getInstance());
                isOaid = true;
            }
            DeviceID.getOAID(this, new IGetter() {
                @Override
                public void onOAIDGetComplete(String result) {
                    if (TextUtils.isEmpty(result)){
                        oaidStr = "";
                    } else {
                        int length = result.length();
                        if (length < 64){
                            for (int i = 0; i < 64 - length; i++){
                                result = result + "0";
                            }
                        }
                        oaidStr = result;
                    }
                    rotateLoading.start();
                    loadingFl.setVisibility(View.VISIBLE);
                    getP().login(phoneStr, verificationStr, ip, oaidStr);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    rotateLoading.start();
                    loadingFl.setVisibility(View.VISIBLE);
                    getP().login(phoneStr, verificationStr, ip, oaidStr);
                }
            });
        });
        getVerificationTv.setOnClickListener(v -> {
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtilXianjinChaoShi.showShort("请输入手机号");
                return;
            }
            if (!XianjinChaoShiStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilXianjinChaoShi.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_xian_jin_chao_shi;
    }

    @Override
    public LoginXianjinChaoShiPresent newP() {
        return new LoginXianjinChaoShiPresent();
    }

    private List<SpanTextVieXianjinChaoShi.BaseSpanModel> createSpanTexts() {
        List<SpanTextVieXianjinChaoShi.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextVieXianjinChaoShi.ClickSpanModel spanModel = new SpanTextVieXianjinChaoShi.ClickSpanModel();
        SpanTextVieXianjinChaoShi.TextSpanModel textSpanModel = new SpanTextVieXianjinChaoShi.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextVieXianjinChaoShi.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
