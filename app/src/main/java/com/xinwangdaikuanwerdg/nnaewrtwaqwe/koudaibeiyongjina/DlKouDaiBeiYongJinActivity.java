package com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjina;

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
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.MainAppKouDaiBeiYongJin;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinapi.HttpKouDaiBeiYongJinApi;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.R;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinm.BaseKouDaiBeiYongJinModel;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinm.ConfigKouDaiBeiYongJinEntity;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinm.DlKouDaiBeiYongJinModel;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.mvp.XActivity;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.net.ApiSubscriber;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.net.NetError;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.net.XApi;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu.MyKouDaiBeiYongJinToast;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu.OpenUtilKouDaiBeiYongJin;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu.KouDaiBeiYongJinPreferencesOpenUtil;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu.StatusBarKouDaiBeiYongJinUtil;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinw.ClickTextViewKouDaiBeiYongJin;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinw.CountDownKouDaiBeiYongJinTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlKouDaiBeiYongJinActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewKouDaiBeiYongJin readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKouDaiBeiYongJinUtil.setTransparent(this, false);
        if (KouDaiBeiYongJinPreferencesOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_kou_dai_bei_yong_jin_loading, null));
        getConfig();
        readTv.setText(OpenUtilKouDaiBeiYongJin.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpKouDaiBeiYongJinApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpKouDaiBeiYongJinApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenUtilKouDaiBeiYongJin.jumpPage(DlKouDaiBeiYongJinActivity.this, JumpH5KouDaiBeiYongJinActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKouDaiBeiYongJinToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKouDaiBeiYongJinToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyKouDaiBeiYongJinToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyKouDaiBeiYongJinToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(MainAppKouDaiBeiYongJin.getInstance());
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
        return R.layout.activity_kou_dai_bei_yong_jin_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpKouDaiBeiYongJinApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilKouDaiBeiYongJin.showErrorInfo(DlKouDaiBeiYongJinActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    KouDaiBeiYongJinPreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpKouDaiBeiYongJinApi.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel<DlKouDaiBeiYongJinModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilKouDaiBeiYongJin.showErrorInfo(DlKouDaiBeiYongJinActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinModel<DlKouDaiBeiYongJinModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    KouDaiBeiYongJinPreferencesOpenUtil.saveString("ip", ip);
                                    KouDaiBeiYongJinPreferencesOpenUtil.saveString("phone", phone);
                                    KouDaiBeiYongJinPreferencesOpenUtil.saveInt("mobileType", mobileType);
                                    OpenUtilKouDaiBeiYongJin.jumpPage(DlKouDaiBeiYongJinActivity.this, MainActivityKouDaiBeiYongJin.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyKouDaiBeiYongJinToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpKouDaiBeiYongJinApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilKouDaiBeiYongJin.showErrorInfo(DlKouDaiBeiYongJinActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinModel baseKouDaiBeiYongJinModel) {
                            if (baseKouDaiBeiYongJinModel != null) {
                                if (baseKouDaiBeiYongJinModel.getCode() == 200) {
                                    MyKouDaiBeiYongJinToast.showShort("验证码发送成功");
                                    CountDownKouDaiBeiYongJinTimer cdt = new CountDownKouDaiBeiYongJinTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
