package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.MainKuaiJieJieKuanNewVoApp;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoapi.HttpKuaiJieJieKuanNewVoApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.BaseKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ConfigKuaiJieJieKuanNewVoEntity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.DlKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.mvp.XActivity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.ApiSubscriber;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.NetError;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.XApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.MyKuaiJieJieKuanNewVoToast;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.OpenKuaiJieJieKuanNewVoUtil;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.PreferencesOpenUtilKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.StatusBarKuaiJieJieKuanNewVoUtil;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvow.ClickTextViewKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvow.CountDownKuaiJieJieKuanNewVoTimer;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlKuaiJieJieKuanNewVoActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewKuaiJieJieKuanNewVo readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKuaiJieJieKuanNewVoUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKuaiJieJieKuanNewVo.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_kuai_jie_jie_kuan_new_op_loading, null));
        getConfig();
        readTv.setText(OpenKuaiJieJieKuanNewVoUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpKuaiJieJieKuanNewVoApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpKuaiJieJieKuanNewVoApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenKuaiJieJieKuanNewVoUtil.jumpPage(DlKuaiJieJieKuanNewVoActivity.this, JumpKuaiJieJieKuanNewVoH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKuaiJieJieKuanNewVoToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyKuaiJieJieKuanNewVoToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyKuaiJieJieKuanNewVoToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyKuaiJieJieKuanNewVoToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(MainKuaiJieJieKuanNewVoApp.getInstance());
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
        return R.layout.activity_dl_kuai_jie_jie_kuan_new_op;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel<ConfigKuaiJieJieKuanNewVoEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieJieKuanNewVoUtil.showErrorInfo(DlKuaiJieJieKuanNewVoActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel<ConfigKuaiJieJieKuanNewVoEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilKuaiJieJieKuanNewVo.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel<DlKuaiJieJieKuanNewVoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieJieKuanNewVoUtil.showErrorInfo(DlKuaiJieJieKuanNewVoActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel<DlKuaiJieJieKuanNewVoModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilKuaiJieJieKuanNewVo.saveString("ip", ip);
                                    PreferencesOpenUtilKuaiJieJieKuanNewVo.saveString("phone", phone);
                                    PreferencesOpenUtilKuaiJieJieKuanNewVo.saveInt("mobileType", mobileType);
                                    OpenKuaiJieJieKuanNewVoUtil.jumpPage(DlKuaiJieJieKuanNewVoActivity.this, MainKuaiJieJieKuanNewVowActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyKuaiJieJieKuanNewVoToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieJieKuanNewVoUtil.showErrorInfo(DlKuaiJieJieKuanNewVoActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel baseKuaiJieJieKuanNewVoModel) {
                            if (baseKuaiJieJieKuanNewVoModel != null) {
                                if (baseKuaiJieJieKuanNewVoModel.getCode() == 200) {
                                    MyKuaiJieJieKuanNewVoToast.showShort("验证码发送成功");
                                    CountDownKuaiJieJieKuanNewVoTimer cdt = new CountDownKuaiJieJieKuanNewVoTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
