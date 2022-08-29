package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewapi.HttpApiMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewBaseModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewConfigEntity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.DlMangGuoHWNewModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mvp.XActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.ApiSubscriber;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.NetError;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.XApi;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MyMangGuoHWNewToast;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MangGuoHWNewOpenUtil;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.StatusBarUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwneww.MangGuoHWNewClickTextView;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwneww.CountDownTimerMangGuoHWNew;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlMangGuoHWNewActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private MangGuoHWNewClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilMangGuoHWNew.setTransparent(this, false);
        if (PreferencesOpenUtilMangGuoHWNew.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_mang_guo_hw_new, null));
        getConfig();
        readTv.setText(MangGuoHWNewOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpApiMangGuoHWNew.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApiMangGuoHWNew.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            MangGuoHWNewOpenUtil.jumpPage(DlMangGuoHWNewActivity.this, JumpH5MangGuoHWNewActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyMangGuoHWNewToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyMangGuoHWNewToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyMangGuoHWNewToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyMangGuoHWNewToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mang_guo_hw_new_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpApiMangGuoHWNew.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            MangGuoHWNewOpenUtil.showErrorInfo(DlMangGuoHWNewActivity.this, error);
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilMangGuoHWNew.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpApiMangGuoHWNew.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel<DlMangGuoHWNewModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            MangGuoHWNewOpenUtil.showErrorInfo(DlMangGuoHWNewActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel<DlMangGuoHWNewModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilMangGuoHWNew.saveString("ip", ip);
                                    PreferencesOpenUtilMangGuoHWNew.saveString("phone", phone);
                                    PreferencesOpenUtilMangGuoHWNew.saveInt("mobileType", mobileType);
                                    MangGuoHWNewOpenUtil.jumpPage(DlMangGuoHWNewActivity.this, MainMangGuoHWNewActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyMangGuoHWNewToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpApiMangGuoHWNew.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            MangGuoHWNewOpenUtil.showErrorInfo(DlMangGuoHWNewActivity.this, error);
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel mangGuoHWNewBaseModel) {
                            if (mangGuoHWNewBaseModel != null) {
                                if (mangGuoHWNewBaseModel.getCode() == 200) {
                                    MyMangGuoHWNewToast.showShort("验证码发送成功");
                                    CountDownTimerMangGuoHWNew cdt = new CountDownTimerMangGuoHWNew(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
