package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa;

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
import com.sdyqwjqwias.fdpwejqwdjew.MainDaiKuanMiaoXiaApp;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi.DaiKuanMiaoXiaHttpApi;
import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DaiKuanMiaoXiaBaseModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ConfigEntityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DlDaiKuanMiaoXiaModel;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;
import com.sdyqwjqwias.fdpwejqwdjew.net.ApiSubscriber;
import com.sdyqwjqwias.fdpwejqwdjew.net.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.MyDaiKuanMiaoXiaToast;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.OpenDaiKuanMiaoXiaUtil;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.PreferencesOpenUtilDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.StatusBarDaiKuanMiaoXiaUtil;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaw.ClickTextViewDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaw.CountDownDaiKuanMiaoXiaTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlDaiKuanMiaoXiaActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewDaiKuanMiaoXia readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarDaiKuanMiaoXiaUtil.setTransparent(this, false);
        if (PreferencesOpenUtilDaiKuanMiaoXia.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_dai_kuan_miao_xia_loading, null));
        getConfig();
        readTv.setText(OpenDaiKuanMiaoXiaUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", DaiKuanMiaoXiaHttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", DaiKuanMiaoXiaHttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenDaiKuanMiaoXiaUtil.jumpPage(DlDaiKuanMiaoXiaActivity.this, JumpH5ActivityDaiKuanMiaoXia.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyDaiKuanMiaoXiaToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyDaiKuanMiaoXiaToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyDaiKuanMiaoXiaToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyDaiKuanMiaoXiaToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(MainDaiKuanMiaoXiaApp.getInstance());
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
        return R.layout.activity_dl_dai_kuan_miao_xia;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            DaiKuanMiaoXiaHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenDaiKuanMiaoXiaUtil.showErrorInfo(DlDaiKuanMiaoXiaActivity.this, error);
                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilDaiKuanMiaoXia.saveString("app_mail", configEntity.getData().getAppMail());
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
            DaiKuanMiaoXiaHttpApi.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel<DlDaiKuanMiaoXiaModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenDaiKuanMiaoXiaUtil.showErrorInfo(DlDaiKuanMiaoXiaActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel<DlDaiKuanMiaoXiaModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilDaiKuanMiaoXia.saveString("ip", ip);
                                    PreferencesOpenUtilDaiKuanMiaoXia.saveString("phone", phone);
                                    PreferencesOpenUtilDaiKuanMiaoXia.saveInt("mobileType", mobileType);
                                    OpenDaiKuanMiaoXiaUtil.jumpPage(DlDaiKuanMiaoXiaActivity.this, MainDaiKuanMiaoXiaActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyDaiKuanMiaoXiaToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            DaiKuanMiaoXiaHttpApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenDaiKuanMiaoXiaUtil.showErrorInfo(DlDaiKuanMiaoXiaActivity.this, error);
                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel daiKuanMiaoXiaBaseModel) {
                            if (daiKuanMiaoXiaBaseModel != null) {
                                if (daiKuanMiaoXiaBaseModel.getCode() == 200) {
                                    MyDaiKuanMiaoXiaToast.showShort("验证码发送成功");
                                    CountDownDaiKuanMiaoXiaTimer cdt = new CountDownDaiKuanMiaoXiaTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
