package com.jinyu.xiaopu.fenfujieui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;
import com.jinyu.xiaopu.fenfujieutils.FenFuJieStatusBarUtil;
import com.jinyu.xiaopu.fenfujieutils.ToastFenFuJieUtil;
import com.jinyu.xiaopu.mvp.XActivity;
import com.jinyu.xiaopu.fenfujiewidget.FenFuJieSpanTextView;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jinyu.xiaopu.ActivityCollector;

import com.jinyu.xiaopu.fenfujiepresent.LoginFenFuJiePresent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginFenFuJieActivity extends XActivity<LoginFenFuJiePresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    FenFuJieSpanTextView loginRemindTv;
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
        FenFuJieStatusBarUtil.setTransparent(this, false);
        if (SharedPreferencesUtilisFenFuJie.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", FenFuJieApi.PRIVACY_POLICY);
                StaticFenFuJieUtil.getValue(LoginFenFuJieActivity.this, WebViewActivityFenFuJie.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", FenFuJieApi.USER_SERVICE_AGREEMENT);
                StaticFenFuJieUtil.getValue(LoginFenFuJieActivity.this, WebViewActivityFenFuJie.class, bundle);
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
                ToastFenFuJieUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticFenFuJieUtil.isValidPhoneNumber(phoneStr)) {
                ToastFenFuJieUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastFenFuJieUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastFenFuJieUtil.showShort("请阅读用户协议及隐私政策");
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
                ToastFenFuJieUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticFenFuJieUtil.isValidPhoneNumber(phoneStr)) {
                ToastFenFuJieUtil.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_fu_jie_login;
    }

    @Override
    public LoginFenFuJiePresent newP() {
        return new LoginFenFuJiePresent();
    }

    private List<FenFuJieSpanTextView.BaseSpanModel> createSpanTexts() {
        List<FenFuJieSpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        FenFuJieSpanTextView.ClickSpanModel spanModel = new FenFuJieSpanTextView.ClickSpanModel();
        FenFuJieSpanTextView.TextSpanModel textSpanModel = new FenFuJieSpanTextView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new FenFuJieSpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
