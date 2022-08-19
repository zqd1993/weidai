package com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan.HttpLingHuoJieHuanApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.BaseModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ConfigEntityLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.DlModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mvp.XActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.ApiSubscriber;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.NetError;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.XApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.MyToastLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanOpenUtil;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.PreferencesOpenUtilLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanStatusBarUtil;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.wlinghuojiekuan.ClickLingHuoJieHuanTextView;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.wlinghuojiekuan.LingHuoJieHuanCountDownTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LingHuoJieHuanDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickLingHuoJieHuanTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        LingHuoJieHuanStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLingHuoJieHuan.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_ling_huo_jie_huan, null));
        getConfig();
        readTv.setText(LingHuoJieHuanOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpLingHuoJieHuanApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpLingHuoJieHuanApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            LingHuoJieHuanOpenUtil.jumpPage(LingHuoJieHuanDlActivity.this, JumpH5LingHuoJieHuanActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastLingHuoJieHuan.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastLingHuoJieHuan.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyToastLingHuoJieHuan.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyToastLingHuoJieHuan.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ling_huo_jie_huan_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpLingHuoJieHuanApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieHuanOpenUtil.showErrorInfo(LingHuoJieHuanDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilLingHuoJieHuan.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpLingHuoJieHuanApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan<DlModelLingHuoJieHuan>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieHuanOpenUtil.showErrorInfo(LingHuoJieHuanDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan<DlModelLingHuoJieHuan> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilLingHuoJieHuan.saveString("ip", ip);
                                    PreferencesOpenUtilLingHuoJieHuan.saveString("phone", phone);
                                    PreferencesOpenUtilLingHuoJieHuan.saveInt("mobileType", mobileType);
                                    LingHuoJieHuanOpenUtil.jumpPage(LingHuoJieHuanDlActivity.this, LingHuoJieHuanMainActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyToastLingHuoJieHuan.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpLingHuoJieHuanApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieHuanOpenUtil.showErrorInfo(LingHuoJieHuanDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan baseModelLingHuoJieHuan) {
                            if (baseModelLingHuoJieHuan != null) {
                                if (baseModelLingHuoJieHuan.getCode() == 200) {
                                    MyToastLingHuoJieHuan.showShort("验证码发送成功");
                                    LingHuoJieHuanCountDownTimer cdt = new LingHuoJieHuanCountDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
