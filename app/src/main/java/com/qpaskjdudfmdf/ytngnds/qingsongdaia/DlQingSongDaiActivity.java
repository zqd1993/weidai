package com.qpaskjdudfmdf.ytngnds.qingsongdaia;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.qpaskjdudfmdf.ytngnds.qingsongdaiapi.HttpApiQingSongDai;
import com.qpaskjdudfmdf.ytngnds.R;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.BaseQingSongDaiModel;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.ConfigEntityQingSongDai;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.DlQingSongDaiModel;
import com.qpaskjdudfmdf.ytngnds.mvp.XActivity;
import com.qpaskjdudfmdf.ytngnds.net.ApiSubscriber;
import com.qpaskjdudfmdf.ytngnds.net.NetError;
import com.qpaskjdudfmdf.ytngnds.net.XApi;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.MyQingSongDaiToast;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.OpenQingSongDaiUtil;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.PreferencesOpenUtilQingSongDai;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.QingSongDaiStatusBarUtil;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiw.ClickTextViewQingSongDai;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiw.CountDownTimerQingSongDai;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlQingSongDaiActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewQingSongDai readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        QingSongDaiStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilQingSongDai.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_qing_song_dai_loading, null));
        getConfig();
        readTv.setText(OpenQingSongDaiUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpApiQingSongDai.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApiQingSongDai.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenQingSongDaiUtil.jumpPage(DlQingSongDaiActivity.this, JumpH5QingSongDaiActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyQingSongDaiToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyQingSongDaiToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyQingSongDaiToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyQingSongDaiToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qing_song_dai_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpApiQingSongDai.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel<ConfigEntityQingSongDai>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenQingSongDaiUtil.showErrorInfo(DlQingSongDaiActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel<ConfigEntityQingSongDai> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilQingSongDai.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpApiQingSongDai.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel<DlQingSongDaiModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenQingSongDaiUtil.showErrorInfo(DlQingSongDaiActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel<DlQingSongDaiModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilQingSongDai.saveString("ip", ip);
                                    PreferencesOpenUtilQingSongDai.saveString("phone", phone);
                                    PreferencesOpenUtilQingSongDai.saveInt("mobileType", mobileType);
                                    OpenQingSongDaiUtil.jumpPage(DlQingSongDaiActivity.this, MainQingSongDaiActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyQingSongDaiToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpApiQingSongDai.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenQingSongDaiUtil.showErrorInfo(DlQingSongDaiActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel baseQingSongDaiModel) {
                            if (baseQingSongDaiModel != null) {
                                if (baseQingSongDaiModel.getCode() == 200) {
                                    MyQingSongDaiToast.showShort("验证码发送成功");
                                    CountDownTimerQingSongDai cdt = new CountDownTimerQingSongDai(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
