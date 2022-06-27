package com.jwcf.zdclm.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jwcf.zdclm.R;
import com.jwcf.zdclm.net.ApiYingJi;
import com.jwcf.zdclm.utils.StaticYingJiUtil;
import com.jwcf.zdclm.utils.StatusBarUtilYingJi;
import com.jwcf.zdclm.utils.ToastYingJiUtil;
import com.jwcf.zdclm.mvp.XActivity;
import com.jwcf.zdclm.router.Router;
import com.jwcf.zdclm.widget.YingJiSpanTextView;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.jwcf.zdclm.ActivityCollector;

import com.jwcf.zdclm.pre.LoginPresentYingJi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YingJiLoginActivity extends XActivity<LoginPresentYingJi> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    YingJiSpanTextView loginRemindTv;
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
        StatusBarUtilYingJi.setTransparent(this, false);
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiYingJi.ZCXY);
                Router.newIntent(YingJiLoginActivity.this)
                        .to(WebViewYingJiActivity.class)
                        .data(bundle)
                        .launch();
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiYingJi.YSZC);
                Router.newIntent(YingJiLoginActivity.this)
                        .to(WebViewYingJiActivity.class)
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
                ToastYingJiUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticYingJiUtil.isValidPhoneNumber(phoneStr)) {
                ToastYingJiUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastYingJiUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastYingJiUtil.showShort("请阅读用户协议及隐私政策");
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
                ToastYingJiUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticYingJiUtil.isValidPhoneNumber(phoneStr)) {
                ToastYingJiUtil.showShort("请输入正确的手机号");
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
    public LoginPresentYingJi newP() {
        return new LoginPresentYingJi();
    }

    private List<YingJiSpanTextView.BaseSpanModel> createSpanTexts() {
        List<YingJiSpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        YingJiSpanTextView.ClickSpanModel spanModel = new YingJiSpanTextView.ClickSpanModel();
        YingJiSpanTextView.TextSpanModel textSpanModel = new YingJiSpanTextView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new YingJiSpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
