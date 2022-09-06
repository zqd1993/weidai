package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma;

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
import com.linghuojieasdufne.vbdsetrrte.LingHuoJieSvsdFdMainApp;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi.HttpApiLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.BaseModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ConfigEntityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.DlModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.mvp.XActivity;
import com.linghuojieasdufne.vbdsetrrte.net.ApiSubscriber;
import com.linghuojieasdufne.vbdsetrrte.net.NetError;
import com.linghuojieasdufne.vbdsetrrte.net.XApi;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.MyToastLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdOpenUtil;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.PreferencesOpenUtilLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdStatusBarUtil;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmw.ClickTextViewLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmw.LingHuoJieSvsdFdCountDownTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LingHuoJieSvsdFdDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewLingHuoJieSvsdFd readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        LingHuoJieSvsdFdStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLingHuoJieSvsdFd.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_ling_huo_jie_djs_urng, null));
        getConfig();
        readTv.setText(LingHuoJieSvsdFdOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpApiLingHuoJieSvsdFd.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApiLingHuoJieSvsdFd.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            LingHuoJieSvsdFdOpenUtil.jumpPage(LingHuoJieSvsdFdDlActivity.this, JumpH5ActivityLingHuoJieSvsdFd.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastLingHuoJieSvsdFd.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastLingHuoJieSvsdFd.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyToastLingHuoJieSvsdFd.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyToastLingHuoJieSvsdFd.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(LingHuoJieSvsdFdMainApp.getInstance());
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
                    login(phoneStr, yzmStr);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    login(phoneStr, yzmStr);
                }
            });
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ling_huo_jie_djs_urng_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpApiLingHuoJieSvsdFd.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieSvsdFdOpenUtil.showErrorInfo(LingHuoJieSvsdFdDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilLingHuoJieSvsdFd.saveString("app_mail", configEntity.getData().getAppMail());
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
            if (xStateController != null)
                xStateController.showLoading();
            HttpApiLingHuoJieSvsdFd.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<DlModelLingHuoJieSvsdFd>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieSvsdFdOpenUtil.showErrorInfo(LingHuoJieSvsdFdDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd<DlModelLingHuoJieSvsdFd> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilLingHuoJieSvsdFd.saveString("ip", ip);
                                    PreferencesOpenUtilLingHuoJieSvsdFd.saveString("phone", phone);
                                    PreferencesOpenUtilLingHuoJieSvsdFd.saveInt("mobileType", mobileType);
                                    LingHuoJieSvsdFdOpenUtil.jumpPage(LingHuoJieSvsdFdDlActivity.this, LingHuoJieSvsdFdMainActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyToastLingHuoJieSvsdFd.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpApiLingHuoJieSvsdFd.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieSvsdFdOpenUtil.showErrorInfo(LingHuoJieSvsdFdDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd baseModelLingHuoJieSvsdFd) {
                            if (baseModelLingHuoJieSvsdFd != null) {
                                if (baseModelLingHuoJieSvsdFd.getCode() == 200) {
                                    MyToastLingHuoJieSvsdFd.showShort("验证码发送成功");
                                    LingHuoJieSvsdFdCountDownTimer cdt = new LingHuoJieSvsdFdCountDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
