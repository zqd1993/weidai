package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StaticZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StatusBarUtilZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.ToastZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.mvp.XActivity;
import com.nsryryasdt.ioerdfjrtu.router.Router;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkawidget.SpanTextViewZhouZhuanXinYongKa;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nsryryasdt.ioerdfjrtu.ActivityCollector;

import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent.LoginZhouZhuanXinYongKaPresent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginZhouZhuanXinYongKaActivity extends XActivity<LoginZhouZhuanXinYongKaPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextViewZhouZhuanXinYongKa loginRemindTv;
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
        StatusBarUtilZhouZhuanXinYongKa.setTransparent(this, false);
        StatusBarUtilZhouZhuanXinYongKa.setLightMode(this);
        isNeedChecked = SharedPreferencesUtilisZhouZhuanXinYongKa.getBoolFromPref("is_agree_check");
        isNeedVerification = SharedPreferencesUtilisZhouZhuanXinYongKa.getBoolFromPref("is_code_register");
        verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
        remindCb.setChecked(isNeedChecked);
        initListener();
//        ToastZhouZhuanXinYongKaUtil.showShort("API_BASE_URL = " + SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + ZhouZhuanXinYongKaApi.API_BASE_URL);
        getP().getCompanyInfo();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ZhouZhuanXinYongKaApi.getZc());
                Router.newIntent(LoginZhouZhuanXinYongKaActivity.this)
                        .to(WebViewActivityZhouZhuanXinYongKa.class)
                        .data(bundle)
                        .launch();
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ZhouZhuanXinYongKaApi.getYs());
                Router.newIntent(LoginZhouZhuanXinYongKaActivity.this)
                        .to(WebViewActivityZhouZhuanXinYongKa.class)
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
                ToastZhouZhuanXinYongKaUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZhouZhuanXinYongKaUtil.isValidPhoneNumber(phoneStr)) {
                ToastZhouZhuanXinYongKaUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastZhouZhuanXinYongKaUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastZhouZhuanXinYongKaUtil.showShort("请阅读用户协议及隐私政策");
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
                ToastZhouZhuanXinYongKaUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZhouZhuanXinYongKaUtil.isValidPhoneNumber(phoneStr)) {
                ToastZhouZhuanXinYongKaUtil.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_zhou_zhuan_xin_yong_ka;
    }

    @Override
    public LoginZhouZhuanXinYongKaPresent newP() {
        return new LoginZhouZhuanXinYongKaPresent();
    }

    private List<SpanTextViewZhouZhuanXinYongKa.BaseSpanModel> createSpanTexts() {
        List<SpanTextViewZhouZhuanXinYongKa.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextViewZhouZhuanXinYongKa.ClickSpanModel spanModel = new SpanTextViewZhouZhuanXinYongKa.ClickSpanModel();
        SpanTextViewZhouZhuanXinYongKa.TextSpanModel textSpanModel = new SpanTextViewZhouZhuanXinYongKa.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextViewZhouZhuanXinYongKa.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
