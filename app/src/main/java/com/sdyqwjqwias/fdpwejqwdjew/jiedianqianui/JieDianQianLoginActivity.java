package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.JieDianQianApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StaticJieDianQianUtil;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StatusBarUtilJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.ToastUtilJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.sdyqwjqwias.fdpwejqwdjew.ActivityCollector;

import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianpresent.JieDianQianLoginPresent;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianwidget.SpanTextViewJieDianQian;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JieDianQianLoginActivity extends XActivity<JieDianQianLoginPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextViewJieDianQian loginRemindTv;
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
        if (JieDianQianSharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtilJieDianQian.setTransparent(this, false);
        StatusBarUtilJieDianQian.setLightMode(this);
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", JieDianQianApi.PRIVACY_POLICY);
                StaticJieDianQianUtil.getValue(this, WebViewActivityJieDianQian.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JieDianQianApi.USER_SERVICE_AGREEMENT);
                StaticJieDianQianUtil.getValue(this, WebViewActivityJieDianQian.class, bundle);
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
                ToastUtilJieDianQian.showShort("请输入手机号");
                return;
            }
            if (!StaticJieDianQianUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilJieDianQian.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilJieDianQian.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked() && isNeedChecked) {
                ToastUtilJieDianQian.showShort("请阅读用户协议及隐私政策");
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
                ToastUtilJieDianQian.showShort("请输入手机号");
                return;
            }
            if (!StaticJieDianQianUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilJieDianQian.showShort("请输入正确的手机号");
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
    public JieDianQianLoginPresent newP() {
        return new JieDianQianLoginPresent();
    }

    private List<SpanTextViewJieDianQian.BaseSpanModel> createSpanTexts() {
        List<SpanTextViewJieDianQian.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextViewJieDianQian.ClickSpanModel spanModel = new SpanTextViewJieDianQian.ClickSpanModel();
        SpanTextViewJieDianQian.TextSpanModel textSpanModel = new SpanTextViewJieDianQian.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《用户注册协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextViewJieDianQian.ClickSpanModel();
        spanModel.setContent("《隐私政策》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
