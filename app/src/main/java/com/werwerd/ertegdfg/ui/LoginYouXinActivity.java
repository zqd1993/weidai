package com.werwerd.ertegdfg.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.YouXinApp;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StaticYouXinUtil;
import com.werwerd.ertegdfg.utils.StatusBarYouXinUtil;
import com.werwerd.ertegdfg.utils.ToastYouXinUtil;
import com.werwerd.ertegdfg.mvp.XActivity;
import com.werwerd.ertegdfg.router.Router;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.werwerd.ertegdfg.ActivityCollector;

import com.werwerd.ertegdfg.present.LoginYouXinPresent;
import com.werwerd.ertegdfg.widget.SpanTextYouXinView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginYouXinActivity extends XActivity<LoginYouXinPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextYouXinView loginRemindTv;
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
        if (SharedPreferencesYouXinUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarYouXinUtil.setTransparent(this, false);
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", Api.PRIVACY_POLICY);
                Router.newIntent(LoginYouXinActivity.this)
                        .to(UserServiceActivity.class)
                        .data(bundle)
                        .launch();
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
                Router.newIntent(LoginYouXinActivity.this)
                        .to(UserServiceActivity.class)
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
                ToastYouXinUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticYouXinUtil.isValidPhoneNumber(phoneStr)) {
                ToastYouXinUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastYouXinUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastYouXinUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(YouXinApp.getInstance());
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
                ToastYouXinUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticYouXinUtil.isValidPhoneNumber(phoneStr)) {
                ToastYouXinUtil.showShort("请输入正确的手机号");
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
    public LoginYouXinPresent newP() {
        return new LoginYouXinPresent();
    }

    private List<SpanTextYouXinView.BaseSpanModel> createSpanTexts() {
        List<SpanTextYouXinView.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextYouXinView.ClickSpanModel spanModel = new SpanTextYouXinView.ClickSpanModel();
        SpanTextYouXinView.TextSpanModel textSpanModel = new SpanTextYouXinView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextYouXinView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
