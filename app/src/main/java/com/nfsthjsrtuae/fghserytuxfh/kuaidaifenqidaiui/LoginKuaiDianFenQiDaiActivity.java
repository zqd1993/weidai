package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.KuaiDianFenQiDaiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.StatusBarUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.ToastUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiwidget.SpanTextViewKuaiDianFenQiDai;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.ActivityCollector;

import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaipresent.LoginKuaiDianFenQiDaiPresent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginKuaiDianFenQiDaiActivity extends XActivity<LoginKuaiDianFenQiDaiPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextViewKuaiDianFenQiDai loginRemindTv;
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
        StatusBarUtilKuaiDianFenQiDai.setTransparent(this, false);
        StatusBarUtilKuaiDianFenQiDai.setLightMode(this);
        isNeedChecked = SharedPreferencesKuaiDianFenQiDaiUtilis.getBoolFromPref("is_agree_check");
        isNeedVerification = SharedPreferencesKuaiDianFenQiDaiUtilis.getBoolFromPref("is_code_register");
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
                bundle.putString("url", ApiKuaiDianFenQiDai.getZc());
                Router.newIntent(LoginKuaiDianFenQiDaiActivity.this)
                        .to(KuaiDianFenQiDaiWebViewActivity.class)
                        .data(bundle)
                        .launch();
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiKuaiDianFenQiDai.getYs());
                Router.newIntent(LoginKuaiDianFenQiDaiActivity.this)
                        .to(KuaiDianFenQiDaiWebViewActivity.class)
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
                ToastUtilKuaiDianFenQiDai.showShort("请输入手机号");
                return;
            }
            if (!KuaiDianFenQiDaiStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilKuaiDianFenQiDai.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilKuaiDianFenQiDai.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtilKuaiDianFenQiDai.showShort("请阅读用户协议及隐私政策");
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
                ToastUtilKuaiDianFenQiDai.showShort("请输入手机号");
                return;
            }
            if (!KuaiDianFenQiDaiStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilKuaiDianFenQiDai.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_kuai_dian_fen_qi_dai;
    }

    @Override
    public LoginKuaiDianFenQiDaiPresent newP() {
        return new LoginKuaiDianFenQiDaiPresent();
    }

    private List<SpanTextViewKuaiDianFenQiDai.BaseSpanModel> createSpanTexts() {
        List<SpanTextViewKuaiDianFenQiDai.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextViewKuaiDianFenQiDai.ClickSpanModel spanModel = new SpanTextViewKuaiDianFenQiDai.ClickSpanModel();
        SpanTextViewKuaiDianFenQiDai.TextSpanModel textSpanModel = new SpanTextViewKuaiDianFenQiDai.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextViewKuaiDianFenQiDai.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
