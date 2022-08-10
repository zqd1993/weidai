package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.FenQiHuanQianBeiApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.SharedPreferencesFenQiHuanQianBeiUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiStaticUtil;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StatusBarUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget.FenQiHuanQianBeiSpanTextView;
import com.mmaeryrusu.qqzdryty.mvp.XActivity;
import com.mmaeryrusu.qqzdryty.router.Router;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.mmaeryrusu.qqzdryty.ActivityCollector;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent.LoginFenQiHuanQianBeiPresent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginFenQiHuanQianBeiActivity extends XActivity<LoginFenQiHuanQianBeiPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    FenQiHuanQianBeiSpanTextView loginRemindTv;
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
        StatusBarUtilFenQiHuanQianBei.setTransparent(this, false);
        StatusBarUtilFenQiHuanQianBei.setLightMode(this);
        isNeedChecked = SharedPreferencesFenQiHuanQianBeiUtilis.getBoolFromPref("is_agree_check");
        isNeedVerification = SharedPreferencesFenQiHuanQianBeiUtilis.getBoolFromPref("is_code_register");
        verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
        remindCb.setChecked(isNeedChecked);
        initListener();
//        ToastUtil.showShort("API_BASE_URL = " + SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + Api.API_BASE_URL);
        getP().getCompanyInfo();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", FenQiHuanQianBeiApi.getZc());
                Router.newIntent(LoginFenQiHuanQianBeiActivity.this)
                        .to(FenQiHuanQianBeiWebViewActivity.class)
                        .data(bundle)
                        .launch();
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", FenQiHuanQianBeiApi.getYs());
                Router.newIntent(LoginFenQiHuanQianBeiActivity.this)
                        .to(FenQiHuanQianBeiWebViewActivity.class)
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
                ToastUtilFenQiHuanQianBei.showShort("请输入手机号");
                return;
            }
            if (!FenQiHuanQianBeiStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilFenQiHuanQianBei.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilFenQiHuanQianBei.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtilFenQiHuanQianBei.showShort("请阅读用户协议及隐私政策");
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
                ToastUtilFenQiHuanQianBei.showShort("请输入手机号");
                return;
            }
            if (!FenQiHuanQianBeiStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilFenQiHuanQianBei.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_qi_huan_qian_login;
    }

    @Override
    public LoginFenQiHuanQianBeiPresent newP() {
        return new LoginFenQiHuanQianBeiPresent();
    }

    private List<FenQiHuanQianBeiSpanTextView.BaseSpanModel> createSpanTexts() {
        List<FenQiHuanQianBeiSpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        FenQiHuanQianBeiSpanTextView.ClickSpanModel spanModel = new FenQiHuanQianBeiSpanTextView.ClickSpanModel();
        FenQiHuanQianBeiSpanTextView.TextSpanModel textSpanModel = new FenQiHuanQianBeiSpanTextView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new FenQiHuanQianBeiSpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
