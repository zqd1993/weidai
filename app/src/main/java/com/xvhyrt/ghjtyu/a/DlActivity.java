package com.xvhyrt.ghjtyu.a;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xvhyrt.ghjtyu.api.HttpApi;
import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.m.BaseModel;
import com.xvhyrt.ghjtyu.m.ConfigEntity;
import com.xvhyrt.ghjtyu.m.DlModel;
import com.xvhyrt.ghjtyu.mvp.XActivity;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.MyToast;
import com.xvhyrt.ghjtyu.u.OpenUtil;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;
import com.xvhyrt.ghjtyu.u.StatusBarUtil;
import com.xvhyrt.ghjtyu.w.ClickTextView;
import com.xvhyrt.ghjtyu.w.CountDownTimer;
import com.lihang.ShadowLayout;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.view_loading, null));
        getConfig();
        readTv.setText(OpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenUtil.jumpPage(DlActivity.this, JumpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()){
                MyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr,yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
            HttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModel<ConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtil.showErrorInfo(DlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModel<ConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
        if (!TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            HttpApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModel<DlModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtil.showErrorInfo(DlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseModel<DlModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    OpenUtil.jumpPage(DlActivity.this, MainActivity.class);
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtil.saveString("ip", ip);
                                    PreferencesOpenUtil.saveString("phone", phone);
                                    PreferencesOpenUtil.saveInt("mobileType", mobileType);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
            HttpApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtil.showErrorInfo(DlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModel baseModel) {
                            if (baseModel != null) {
                                if (baseModel.getCode() == 200) {
                                    MyToast.showShort("验证码发送成功");
                                    CountDownTimer cdt = new CountDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }
}
