package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaa;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.sdjzpqasdjsdndsfhds.zms.R;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.KuaiJieKuanBaseModel;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.ConfigEntityKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.DlKuaiJieKuanModel;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.MyKuaiJieKuanToast;
import com.sdjzpqasdjsdndsfhds.zms.mvp.XActivity;
import com.sdjzpqasdjsdndsfhds.zms.net.ApiSubscriber;
import com.sdjzpqasdjsdndsfhds.zms.net.NetError;
import com.sdjzpqasdjsdndsfhds.zms.net.XApi;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.OpenKuaiJieKuanUtil;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.PreferencesOpenUtilKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.StatusBarKuaiJieKuanUtil;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaw.ClickTextViewKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaw.CountDownKuaiJieKuanTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlKuaiJieKuanActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewKuaiJieKuan readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKuaiJieKuanUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKuaiJieKuan.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_kuai_jie_kuan_loading, null));
        getConfig();
        readTv.setText(OpenKuaiJieKuanUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", KuaiJieKuanHttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", KuaiJieKuanHttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenKuaiJieKuanUtil.jumpPage(DlKuaiJieKuanActivity.this, JumpH5ActivityKuaiJieKuan.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKuaiJieKuanToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKuaiJieKuanToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyKuaiJieKuanToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyKuaiJieKuanToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dl_kuai_jie_kuan;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            KuaiJieKuanHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieKuanUtil.showErrorInfo(DlKuaiJieKuanActivity.this, error);
                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilKuaiJieKuan.saveString("app_mail", configEntity.getData().getAppMail());
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
            KuaiJieKuanHttpApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel<DlKuaiJieKuanModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieKuanUtil.showErrorInfo(DlKuaiJieKuanActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel<DlKuaiJieKuanModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilKuaiJieKuan.saveString("ip", ip);
                                    PreferencesOpenUtilKuaiJieKuan.saveString("phone", phone);
                                    PreferencesOpenUtilKuaiJieKuan.saveInt("mobileType", mobileType);
                                    OpenKuaiJieKuanUtil.jumpPage(DlKuaiJieKuanActivity.this, MainKuaiJieKuanActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyKuaiJieKuanToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            KuaiJieKuanHttpApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieKuanUtil.showErrorInfo(DlKuaiJieKuanActivity.this, error);
                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel kuaiJieKuanBaseModel) {
                            if (kuaiJieKuanBaseModel != null) {
                                if (kuaiJieKuanBaseModel.getCode() == 200) {
                                    MyKuaiJieKuanToast.showShort("验证码发送成功");
                                    CountDownKuaiJieKuanTimer cdt = new CountDownKuaiJieKuanTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}