package com.fdhsdjqqhds.ppfdzabsdvd.qufenqia;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi.HttpApiQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.BaseQuFenQiModel;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.QuFenQiConfigEntity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.DlQuFenQiModel;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.net.ApiSubscriber;
import com.fdhsdjqqhds.ppfdzabsdvd.net.NetError;
import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiMyToast;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.OpenUtilQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiStatusBarUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiw.ClickTextViewQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiw.CountQuFenQiDownTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuFenQiDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewQuFenQi readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        QuFenQiStatusBarUtil.setTransparent(this, false);
        if (PreferencesQuFenQiOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.view_qu_fen_qi_loading, null));
        getConfig();
        readTv.setText(OpenUtilQuFenQi.createDlSpanTexts(), position -> {
            if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("AGREEMENT"))) {
                bundle = new Bundle();
                if (position == 1) {
                    bundle.putString("url", PreferencesQuFenQiOpenUtil.getString("AGREEMENT") + HttpApiQuFenQi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                } else {
                    bundle.putString("url", PreferencesQuFenQiOpenUtil.getString("AGREEMENT") + HttpApiQuFenQi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                }
                OpenUtilQuFenQi.getValue(QuFenQiDlActivity.this, QuFenQiJumpH5Activity.class, bundle);
            }
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                QuFenQiMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                QuFenQiMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                QuFenQiMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                QuFenQiMyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qu_fen_qi_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
            HttpApiQuFenQi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQuFenQiModel<QuFenQiConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilQuFenQi.showErrorInfo(QuFenQiDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseQuFenQiModel<QuFenQiConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesQuFenQiOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
                                    if ("0".equals(configEntity.getData().getIsCodeLogin())) {
                                        yzmCv.setVisibility(View.GONE);
                                    } else {
                                        yzmCv.setVisibility(View.VISIBLE);
                                    }
                                    isNeedYzm = "1".equals(configEntity.getData().getIsCodeLogin());
                                    isChecked = "1".equals(configEntity.getData().getIsSelectLogin());
                                    remindCb.setChecked(isChecked);
                                }
                            }
                        }
                    });
        }
    }

    private void getIp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://pv.sohu.com/cityjson")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            ip = jsonObject.getString("cip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(String phone, String verificationStr) {
        if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            HttpApiQuFenQi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQuFenQiModel<DlQuFenQiModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilQuFenQi.showErrorInfo(QuFenQiDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseQuFenQiModel<DlQuFenQiModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesQuFenQiOpenUtil.saveString("ip", ip);
                                    PreferencesQuFenQiOpenUtil.saveString("phone", phone);
                                    PreferencesQuFenQiOpenUtil.saveInt("mobileType", mobileType);
                                    OpenUtilQuFenQi.getValue(QuFenQiDlActivity.this, MainActivityQuFenQi.class, null, true);
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    QuFenQiMyToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
            HttpApiQuFenQi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQuFenQiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilQuFenQi.showErrorInfo(QuFenQiDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseQuFenQiModel baseQuFenQiModel) {
                            if (baseQuFenQiModel != null) {
                                if (baseQuFenQiModel.getCode() == 200) {
                                    QuFenQiMyToast.showShort("验证码发送成功");
                                    CountQuFenQiDownTimer cdt = new CountQuFenQiDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }
}
