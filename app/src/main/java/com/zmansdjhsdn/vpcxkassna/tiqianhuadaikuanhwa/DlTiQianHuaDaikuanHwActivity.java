package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa;

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
import com.zmansdjhsdn.vpcxkassna.MainTiQianHuaDaikuanHwApp;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwapi.HttpTiQianHuaDaikuanHwApi;
import com.zmansdjhsdn.vpcxkassna.R;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.BaseTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ConfigTiQianHuaDaikuanHwEntity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.DlTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.mvp.XActivity;
import com.zmansdjhsdn.vpcxkassna.net.ApiSubscriber;
import com.zmansdjhsdn.vpcxkassna.net.NetError;
import com.zmansdjhsdn.vpcxkassna.net.XApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.MyTiQianHuaDaikuanHwToast;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.OpenTiQianHuaDaikuanHwUtil;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.PreferencesOpenUtilTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.StatusBarTiQianHuaDaikuanHwUtil;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhww.ClickTextViewTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhww.CountDownTiQianHuaDaikuanHwTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlTiQianHuaDaikuanHwActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewTiQianHuaDaikuanHw readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarTiQianHuaDaikuanHwUtil.setTransparent(this, false);
        if (PreferencesOpenUtilTiQianHuaDaikuanHw.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_ti_qian_hua_dai_kuan_hw_loading, null));
        getConfig();
        readTv.setText(OpenTiQianHuaDaikuanHwUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpTiQianHuaDaikuanHwApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpTiQianHuaDaikuanHwApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenTiQianHuaDaikuanHwUtil.jumpPage(DlTiQianHuaDaikuanHwActivity.this, JumpTiQianHuaDaikuanHwH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyTiQianHuaDaikuanHwToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyTiQianHuaDaikuanHwToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyTiQianHuaDaikuanHwToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyTiQianHuaDaikuanHwToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(MainTiQianHuaDaikuanHwApp.getInstance());
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
        return R.layout.activity_dl_ti_qian_hua_dai_kuan_hw;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenTiQianHuaDaikuanHwUtil.showErrorInfo(DlTiQianHuaDaikuanHwActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilTiQianHuaDaikuanHw.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel<DlTiQianHuaDaikuanHwModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenTiQianHuaDaikuanHwUtil.showErrorInfo(DlTiQianHuaDaikuanHwActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel<DlTiQianHuaDaikuanHwModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilTiQianHuaDaikuanHw.saveString("ip", ip);
                                    PreferencesOpenUtilTiQianHuaDaikuanHw.saveString("phone", phone);
                                    PreferencesOpenUtilTiQianHuaDaikuanHw.saveInt("mobileType", mobileType);
                                    OpenTiQianHuaDaikuanHwUtil.jumpPage(DlTiQianHuaDaikuanHwActivity.this, MainTiQianHuaDaikuanHwActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyTiQianHuaDaikuanHwToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenTiQianHuaDaikuanHwUtil.showErrorInfo(DlTiQianHuaDaikuanHwActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel baseTiQianHuaDaikuanHwModel) {
                            if (baseTiQianHuaDaikuanHwModel != null) {
                                if (baseTiQianHuaDaikuanHwModel.getCode() == 200) {
                                    MyTiQianHuaDaikuanHwToast.showShort("验证码发送成功");
                                    CountDownTiQianHuaDaikuanHwTimer cdt = new CountDownTiQianHuaDaikuanHwTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
