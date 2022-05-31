package com.aklsfasad.fsjhfkk.a;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.aklsfasad.fsjhfkk.api.HttpApi;
import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.m.BaseModel;
import com.aklsfasad.fsjhfkk.m.ConfigEntity;
import com.aklsfasad.fsjhfkk.m.DlModel;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.net.ApiSubscriber;
import com.aklsfasad.fsjhfkk.net.NetError;
import com.aklsfasad.fsjhfkk.net.XApi;
import com.aklsfasad.fsjhfkk.router.Router;
import com.aklsfasad.fsjhfkk.u.MyToast;
import com.aklsfasad.fsjhfkk.u.OpenUtil;
import com.aklsfasad.fsjhfkk.u.PreferencesOpenUtil;
import com.aklsfasad.fsjhfkk.w.ClickTextView;
import com.aklsfasad.fsjhfkk.w.CountDownTimer;
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
    private ShadowLayout yzmCv;

    private String phoneStr, verificationStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
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
            if (position == 0) {
                bundle.putString("url", HttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenUtil.jumpPage(DlActivity.this, JumpH5Activity.class, bundle);
        });
        getYzmTv.setOnClickListener(v -> {

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
                                isChecked = "1".equals(configEntity.getData().getIsSelectLogin());
                                isNeedYzm = "1".equals(configEntity.getData().getIsCodeLogin());
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
                                OpenUtil.jumpPage(DlActivity.this, DlActivity.class);
                                int mobileType = dlModel.getData().getMobileType();
                                PreferencesOpenUtil.saveString("ip", ip);
                                PreferencesOpenUtil.saveString("phone", phone);
                                PreferencesOpenUtil.saveInt("mobileType", mobileType);
                                finish();
                            }
                        } else {
                            if (dlModel.getCode() == 500){
                                MyToast.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    public void getYzm(String phone) {
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
