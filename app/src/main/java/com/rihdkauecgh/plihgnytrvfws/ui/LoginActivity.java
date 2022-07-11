package com.rihdkauecgh.plihgnytrvfws.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtil;
import com.rihdkauecgh.plihgnytrvfws.utils.StatusBarUtil;
import com.rihdkauecgh.plihgnytrvfws.utils.ToastUtil;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.rihdkauecgh.plihgnytrvfws.ActivityCollector;

import com.rihdkauecgh.plihgnytrvfws.present.LoginPresent;
import com.rihdkauecgh.plihgnytrvfws.widget.SpanTextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends XActivity<LoginPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextView loginRemindTv;
    @BindView(R.id.remind_cb)
    public CheckBox remindCb;
    @BindView(R.id.rotate_loading)
    public RotateLoading rotateLoading;
    @BindView(R.id.loading_fl)
    public View loadingFl;
    @BindView(R.id.verification_ll)
    public View verificationLl;

    private String phoneStr, verificationStr, ip = "";
    private Bundle bundle;
    public boolean isNeedChecked = true, isNeedVerification = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (SharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtil.setTransparent(this, false);
        StatusBarUtil.setLightMode(this);
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("AGREEMENT"))) {
                if (position == 1) {
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", SharedPreferencesUtilis.getStringFromPref("AGREEMENT") + Api.PRIVACY_POLICY);
                    StaticUtil.getValue(this, WebViewActivity.class, bundle);
                } else {
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", SharedPreferencesUtilis.getStringFromPref("AGREEMENT") + Api.USER_SERVICE_AGREEMENT);
                    StaticUtil.getValue(this, WebViewActivity.class, bundle);
                }
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
                ToastUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked() && isNeedChecked) {
                ToastUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            getP().login(phoneStr, verificationStr, ip);

        });
        getVerificationTv.setOnClickListener(v -> {
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtil.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent();
    }

    private List<SpanTextView.BaseSpanModel> createSpanTexts() {
        List<SpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextView.ClickSpanModel spanModel = new SpanTextView.ClickSpanModel();
        SpanTextView.TextSpanModel textSpanModel = new SpanTextView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《用户注册协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextView.ClickSpanModel();
        spanModel.setContent("《隐私政策》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
