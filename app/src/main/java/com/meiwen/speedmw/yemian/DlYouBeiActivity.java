package com.meiwen.speedmw.yemian;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.meiwen.speedmw.chajian.YouBeiClickTextView;
import com.meiwen.speedmw.jiekou.HttpYouBeiApi;
import com.meiwen.speedmw.R;
import com.meiwen.speedmw.moxing.BaseYouBeiModel;
import com.meiwen.speedmw.moxing.ConfigYouBeiEntity;
import com.meiwen.speedmw.moxing.DlYouBeiModel;
import com.meiwen.speedmw.mvp.XActivity;
import com.meiwen.speedmw.net.ApiSubscriber;
import com.meiwen.speedmw.net.NetError;
import com.meiwen.speedmw.net.XApi;
import com.meiwen.speedmw.gongju.MyYouBeiToast;
import com.meiwen.speedmw.gongju.OpenYouBeiUtil;
import com.meiwen.speedmw.gongju.PreferencesYouBeiOpenUtil;
import com.meiwen.speedmw.gongju.StatusYouBeiBarUtil;
import com.meiwen.speedmw.chajian.YouBeiCountDownTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlYouBeiActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private YouBeiClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusYouBeiBarUtil.setTransparent(this, false);
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
        new Handler().postDelayed(() -> {
            getConfig();
        }, 200);
        readTv.setText(OpenYouBeiUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpYouBeiApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpYouBeiApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenYouBeiUtil.jumpPage(DlYouBeiActivity.this, JumpH5YouBeiActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyYouBeiToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyYouBeiToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyYouBeiToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyYouBeiToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
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
        if (!TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
            HttpYouBeiApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseYouBeiModel<ConfigYouBeiEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenYouBeiUtil.showErrorInfo(DlYouBeiActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseYouBeiModel<ConfigYouBeiEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesYouBeiOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
        if (!TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            HttpYouBeiApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseYouBeiModel<DlYouBeiModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenYouBeiUtil.showErrorInfo(DlYouBeiActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseYouBeiModel<DlYouBeiModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    OpenYouBeiUtil.jumpPage(DlYouBeiActivity.this, MainYouBeiActivity.class);
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesYouBeiOpenUtil.saveString("ip", ip);
                                    PreferencesYouBeiOpenUtil.saveString("phone", phone);
                                    PreferencesYouBeiOpenUtil.saveInt("mobileType", mobileType);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyYouBeiToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
            HttpYouBeiApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseYouBeiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenYouBeiUtil.showErrorInfo(DlYouBeiActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseYouBeiModel baseYouBeiModel) {
                            if (baseYouBeiModel != null) {
                                if (baseYouBeiModel.getCode() == 200) {
                                    MyYouBeiToast.showShort("验证码发送成功");
                                    YouBeiCountDownTimer cdt = new YouBeiCountDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }
}
