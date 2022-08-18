package com.asdfgaery.nnaeryaery.daikuanqianbaoui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.DaiKuanQianBaoApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.ToastUtilDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.StaticDaiKuanQianBaoUtil;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.DaiKuanQianBaoStatusBarUtil;
import com.asdfgaery.nnaeryaery.daikuanqianbaowidget.DaiKuanQianBaoSpanTextView;
import com.asdfgaery.nnaeryaery.mvp.XActivity;
import com.asdfgaery.nnaeryaery.router.Router;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.asdfgaery.nnaeryaery.ActivityCollector;

import com.asdfgaery.nnaeryaery.daikuanqianbaopresent.LoginPresentDaiKuanQianBao;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DaiKuanQianBaoLoginActivity extends XActivity<LoginPresentDaiKuanQianBao> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    DaiKuanQianBaoSpanTextView loginRemindTv;
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
        DaiKuanQianBaoStatusBarUtil.setTransparent(this, false);
        DaiKuanQianBaoStatusBarUtil.setLightMode(this);
        isNeedChecked = SharedDaiKuanQianBaoPreferencesUtilis.getBoolFromPref("is_agree_check");
        isNeedVerification = SharedDaiKuanQianBaoPreferencesUtilis.getBoolFromPref("is_code_register");
        verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
        remindCb.setChecked(isNeedChecked);
        initListener();
//        DiXiDaiKuanToastUtil.showShort("API_BASE_URL = " + SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + DiXiDaiKuanApi.API_BASE_URL);
        getP().getCompanyInfo();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", DaiKuanQianBaoApi.getZc());
                Router.newIntent(DaiKuanQianBaoLoginActivity.this)
                        .to(WebViewActivityDaiKuanQianBao.class)
                        .data(bundle)
                        .launch();
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", DaiKuanQianBaoApi.getYs());
                Router.newIntent(DaiKuanQianBaoLoginActivity.this)
                        .to(WebViewActivityDaiKuanQianBao.class)
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
                ToastUtilDaiKuanQianBao.showShort("请输入手机号");
                return;
            }
            if (!StaticDaiKuanQianBaoUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilDaiKuanQianBao.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilDaiKuanQianBao.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtilDaiKuanQianBao.showShort("请阅读用户协议及隐私政策");
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
                ToastUtilDaiKuanQianBao.showShort("请输入手机号");
                return;
            }
            if (!StaticDaiKuanQianBaoUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilDaiKuanQianBao.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_dai_kuan_qian_bao;
    }

    @Override
    public LoginPresentDaiKuanQianBao newP() {
        return new LoginPresentDaiKuanQianBao();
    }

    private List<DaiKuanQianBaoSpanTextView.BaseSpanModel> createSpanTexts() {
        List<DaiKuanQianBaoSpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        DaiKuanQianBaoSpanTextView.ClickSpanModel spanModel = new DaiKuanQianBaoSpanTextView.ClickSpanModel();
        DaiKuanQianBaoSpanTextView.TextSpanModel textSpanModel = new DaiKuanQianBaoSpanTextView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new DaiKuanQianBaoSpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
