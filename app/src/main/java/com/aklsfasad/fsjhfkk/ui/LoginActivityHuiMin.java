package com.aklsfasad.fsjhfkk.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.StatusBarUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.ToastUtilHuiMin;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.router.Router;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.aklsfasad.fsjhfkk.ActivityCollector;

import com.aklsfasad.fsjhfkk.present.LoginPresentHuiMin;
import com.aklsfasad.fsjhfkk.widget.SpanTextViewHuiMin;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivityHuiMin extends XActivity<LoginPresentHuiMin> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextViewHuiMin loginRemindTv;
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
        StatusBarUtilHuiMin.setTransparent(this, false);
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (!TextUtils.isEmpty(SharedPreferencesUtilisHuiMin.getStringFromPref("AGREEMENT"))) {
                if (position == 1) {
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", SharedPreferencesUtilisHuiMin.getStringFromPref("AGREEMENT") + Api.PRIVACY_POLICY);
                    Router.newIntent(LoginActivityHuiMin.this)
                            .to(WebHuiMinActivity.class)
                            .data(bundle)
                            .launch();
                } else {
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", SharedPreferencesUtilisHuiMin.getStringFromPref("AGREEMENT") + Api.USER_SERVICE_AGREEMENT);
                    Router.newIntent(LoginActivityHuiMin.this)
                            .to(WebHuiMinActivity.class)
                            .data(bundle)
                            .launch();
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

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return px
     */
    public static int sp2px(float spValue) {
        return (int) (0.5f + spValue * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresentHuiMin newP() {
        return new LoginPresentHuiMin();
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
                ToastUtilHuiMin.showShort("请输入手机号");
                return;
            }
            if (!StaticUtilHuiMin.isValidPhoneNumber(phoneStr)) {
                ToastUtilHuiMin.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilHuiMin.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtilHuiMin.showShort("请阅读用户协议及隐私政策");
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
                ToastUtilHuiMin.showShort("请输入手机号");
                return;
            }
            if (!StaticUtilHuiMin.isValidPhoneNumber(phoneStr)) {
                ToastUtilHuiMin.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    private List<SpanTextViewHuiMin.BaseSpanModel> createSpanTexts() {
        List<SpanTextViewHuiMin.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextViewHuiMin.ClickSpanModel spanModel = new SpanTextViewHuiMin.ClickSpanModel();
        SpanTextViewHuiMin.TextSpanModel textSpanModel = new SpanTextViewHuiMin.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextViewHuiMin.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
