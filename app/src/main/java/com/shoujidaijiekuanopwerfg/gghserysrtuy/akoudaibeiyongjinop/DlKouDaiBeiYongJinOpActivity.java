package com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.apikoudaibeiyongjinop.HttpKouDaiBeiYongJinOpApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.R;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.BaseKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ConfigKouDaiBeiYongJinOpEntity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.DlKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mvp.XActivity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.ApiSubscriber;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.NetError;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.XApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.MyKouDaiBeiYongJinOpToast;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.OpenKouDaiBeiYongJinOpUtil;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.PreferencesOpenUtilKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.StatusBarKouDaiBeiYongJinOpUtil;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.wkoudaibeiyongjinop.ClickTextViewKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.wkoudaibeiyongjinop.CountDownKouDaiBeiYongJinOpTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlKouDaiBeiYongJinOpActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewKouDaiBeiYongJinOp readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKouDaiBeiYongJinOpUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKouDaiBeiYongJinOp.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_kou_dai_bei_yong_jin_op_loading, null));
        getConfig();
        readTv.setText(OpenKouDaiBeiYongJinOpUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpKouDaiBeiYongJinOpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpKouDaiBeiYongJinOpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenKouDaiBeiYongJinOpUtil.jumpPage(DlKouDaiBeiYongJinOpActivity.this, JumpKouDaiBeiYongJinOpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKouDaiBeiYongJinOpToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKouDaiBeiYongJinOpToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyKouDaiBeiYongJinOpToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyKouDaiBeiYongJinOpToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dl_kou_dai_bei_yong_jin_op;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKouDaiBeiYongJinOpUtil.showErrorInfo(DlKouDaiBeiYongJinOpActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilKouDaiBeiYongJinOp.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel<DlKouDaiBeiYongJinOpModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKouDaiBeiYongJinOpUtil.showErrorInfo(DlKouDaiBeiYongJinOpActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel<DlKouDaiBeiYongJinOpModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilKouDaiBeiYongJinOp.saveString("ip", ip);
                                    PreferencesOpenUtilKouDaiBeiYongJinOp.saveString("phone", phone);
                                    PreferencesOpenUtilKouDaiBeiYongJinOp.saveInt("mobileType", mobileType);
                                    OpenKouDaiBeiYongJinOpUtil.jumpPage(DlKouDaiBeiYongJinOpActivity.this, MainKouDaiBeiYongJinOpwActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyKouDaiBeiYongJinOpToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKouDaiBeiYongJinOpUtil.showErrorInfo(DlKouDaiBeiYongJinOpActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel baseKouDaiBeiYongJinOpModel) {
                            if (baseKouDaiBeiYongJinOpModel != null) {
                                if (baseKouDaiBeiYongJinOpModel.getCode() == 200) {
                                    MyKouDaiBeiYongJinOpToast.showShort("验证码发送成功");
                                    CountDownKouDaiBeiYongJinOpTimer cdt = new CountDownKouDaiBeiYongJinOpTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
