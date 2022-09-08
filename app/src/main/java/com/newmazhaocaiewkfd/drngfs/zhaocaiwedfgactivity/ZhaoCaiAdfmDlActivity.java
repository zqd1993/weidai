package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity;

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
import com.newmazhaocaiewkfd.drngfs.MainAppZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgapi.ZhaoCaiAdfmHttpApi;
import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BaseModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ConfigZhaoCaiAdfmEntity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.DlZhaoCaiAdfmModel;
import com.newmazhaocaiewkfd.drngfs.mvp.XActivity;
import com.newmazhaocaiewkfd.drngfs.net.ApiSubscriber;
import com.newmazhaocaiewkfd.drngfs.net.NetError;
import com.newmazhaocaiewkfd.drngfs.net.XApi;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.MyZhaoCaiAdfmToas;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.OpenZhaoCaiAdfmUtil;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.StatusBarZhaoCaiAdfmUtil;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgweidgt.ClickTextViewZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgweidgt.CountDownZhaoCaiAdfmTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ZhaoCaiAdfmDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewZhaoCaiAdfm readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarZhaoCaiAdfmUtil.setTransparent(this, false);
        if (PreferencesOpenUtilZhaoCaiAdfm.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_zhao_cai_endfi_weng_loading, null));
        getConfig();
        readTv.setText(OpenZhaoCaiAdfmUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", ZhaoCaiAdfmHttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", ZhaoCaiAdfmHttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenZhaoCaiAdfmUtil.getValue(ZhaoCaiAdfmDlActivity.this, ZhaoCaiAdfmJumpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyZhaoCaiAdfmToas.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyZhaoCaiAdfmToas.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyZhaoCaiAdfmToas.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyZhaoCaiAdfmToas.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(MainAppZhaoCaiAdfm.getInstance());
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
        return R.layout.activity_xiang_zhao_cai_endfi_weng;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenZhaoCaiAdfmUtil.showErrorInfo(ZhaoCaiAdfmDlActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilZhaoCaiAdfm.saveString("app_mail", configEntity.getData().getAppMail());
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
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<DlZhaoCaiAdfmModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenZhaoCaiAdfmUtil.showErrorInfo(ZhaoCaiAdfmDlActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<DlZhaoCaiAdfmModel> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                int mobileType = dlModel.getData().getMobileType();
                                PreferencesOpenUtilZhaoCaiAdfm.saveString("ip", ip);
                                PreferencesOpenUtilZhaoCaiAdfm.saveString("phone", phone);
                                PreferencesOpenUtilZhaoCaiAdfm.saveInt("mobileType", mobileType);
                                OpenZhaoCaiAdfmUtil.getValue(ZhaoCaiAdfmDlActivity.this, MainZhaoCaiAdfmActivity.class, null, true);
                            }
                        } else {
                            if (dlModel.getCode() == 500) {
                                MyZhaoCaiAdfmToas.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    public void getYzm(String phone) {
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenZhaoCaiAdfmUtil.showErrorInfo(ZhaoCaiAdfmDlActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm baseModelZhaoCaiAdfm) {
                        if (baseModelZhaoCaiAdfm != null) {
                            if (baseModelZhaoCaiAdfm.getCode() == 200) {
                                MyZhaoCaiAdfmToas.showShort("验证码发送成功");
                                CountDownZhaoCaiAdfmTimer cdt = new CountDownZhaoCaiAdfmTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }
}
