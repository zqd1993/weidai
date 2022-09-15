package com.dlproject.jxdk.lejieqianbaoa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dlproject.jxdk.LeJieQianBaoMainApp;
import com.dlproject.jxdk.lejieqianbaoapi.HttpApiLeJieQianBao;
import com.dlproject.jxdk.R;
import com.dlproject.jxdk.lejieqianbaom.BaseModelLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.ConfigEntityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.DlModelLeJieQianBao;
import com.dlproject.jxdk.mvp.XActivity;
import com.dlproject.jxdk.net.ApiSubscriber;
import com.dlproject.jxdk.net.NetError;
import com.dlproject.jxdk.net.XApi;
import com.dlproject.jxdk.lejieqianbaou.MyToastLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoOpenUtil;
import com.dlproject.jxdk.lejieqianbaou.PreferencesOpenUtilLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoStatusBarUtil;
import com.dlproject.jxdk.lejieqianbaow.ClickTextViewLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaow.LeJieQianBaoCountDownTimer;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeJieQianBaoDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewLeJieQianBao readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        LeJieQianBaoStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLeJieQianBao.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_le_jie_qian_bao, null));
        getConfig();
        readTv.setText(LeJieQianBaoOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpApiLeJieQianBao.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApiLeJieQianBao.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            LeJieQianBaoOpenUtil.jumpPage(LeJieQianBaoDlActivity.this, JumpH5ActivityLeJieQianBao.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastLeJieQianBao.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastLeJieQianBao.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyToastLeJieQianBao.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyToastLeJieQianBao.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(LeJieQianBaoMainApp.getInstance());
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
        return R.layout.activity_le_jie_qian_bao_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpApiLeJieQianBao.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao<ConfigEntityLeJieQianBao>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LeJieQianBaoOpenUtil.showErrorInfo(LeJieQianBaoDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao<ConfigEntityLeJieQianBao> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilLeJieQianBao.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpApiLeJieQianBao.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao<DlModelLeJieQianBao>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LeJieQianBaoOpenUtil.showErrorInfo(LeJieQianBaoDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao<DlModelLeJieQianBao> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilLeJieQianBao.saveString("ip", ip);
                                    PreferencesOpenUtilLeJieQianBao.saveString("phone", phone);
                                    PreferencesOpenUtilLeJieQianBao.saveInt("mobileType", mobileType);
                                    LeJieQianBaoOpenUtil.jumpPage(LeJieQianBaoDlActivity.this, LeJieQianBaoMainActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyToastLeJieQianBao.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpApiLeJieQianBao.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao>() {
                        @Override
                        protected void onFail(NetError error) {
                            LeJieQianBaoOpenUtil.showErrorInfo(LeJieQianBaoDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao baseModelLeJieQianBao) {
                            if (baseModelLeJieQianBao != null) {
                                if (baseModelLeJieQianBao.getCode() == 200) {
                                    MyToastLeJieQianBao.showShort("验证码发送成功");
                                    LeJieQianBaoCountDownTimer cdt = new LeJieQianBaoCountDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
