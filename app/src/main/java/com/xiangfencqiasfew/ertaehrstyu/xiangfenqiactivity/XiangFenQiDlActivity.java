package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiapi.XiangFenQiHttpApi;
import com.xiangfencqiasfew.ertaehrstyu.R;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BaseModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ConfigXiangFenQiEntity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.DlXiangFenQiModel;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XActivity;
import com.xiangfencqiasfew.ertaehrstyu.net.ApiSubscriber;
import com.xiangfencqiasfew.ertaehrstyu.net.NetError;
import com.xiangfencqiasfew.ertaehrstyu.net.XApi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.MyXiangFenQiToast;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.OpenXiangFenQiUtil;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.PreferencesOpenUtilXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.StatusBarXiangFenQiUtil;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiweidgt.ClickTextViewXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiweidgt.CountDownXiangFenQiTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XiangFenQiDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewXiangFenQi readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarXiangFenQiUtil.setTransparent(this, false);
        if (PreferencesOpenUtilXiangFenQi.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_xiang_fen_qi_loading, null));
        getConfig();
        readTv.setText(OpenXiangFenQiUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", XiangFenQiHttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", XiangFenQiHttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenXiangFenQiUtil.getValue(XiangFenQiDlActivity.this, XiangFenQiJumpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyXiangFenQiToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyXiangFenQiToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyXiangFenQiToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyXiangFenQiToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xiang_fen_qi_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        XiangFenQiHttpApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<ConfigXiangFenQiEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenXiangFenQiUtil.showErrorInfo(XiangFenQiDlActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<ConfigXiangFenQiEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilXiangFenQi.saveString("app_mail", configEntity.getData().getAppMail());
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
        XiangFenQiHttpApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<DlXiangFenQiModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenXiangFenQiUtil.showErrorInfo(XiangFenQiDlActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<DlXiangFenQiModel> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                int mobileType = dlModel.getData().getMobileType();
                                PreferencesOpenUtilXiangFenQi.saveString("ip", ip);
                                PreferencesOpenUtilXiangFenQi.saveString("phone", phone);
                                PreferencesOpenUtilXiangFenQi.saveInt("mobileType", mobileType);
                                OpenXiangFenQiUtil.getValue(XiangFenQiDlActivity.this, MainXiangFenQiActivity.class, null, true);
                            }
                        } else {
                            if (dlModel.getCode() == 500) {
                                MyXiangFenQiToast.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    public void getYzm(String phone) {
        XiangFenQiHttpApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenXiangFenQiUtil.showErrorInfo(XiangFenQiDlActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi baseModelXiangFenQi) {
                        if (baseModelXiangFenQi != null) {
                            if (baseModelXiangFenQi.getCode() == 200) {
                                MyXiangFenQiToast.showShort("验证码发送成功");
                                CountDownXiangFenQiTimer cdt = new CountDownXiangFenQiTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }
}
