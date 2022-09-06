package com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqivioa;

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
import com.haojieopdzfgvaewt.bbsdgrgdz.MainFenQiXiangVioApp;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.R;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviom.FenQiXiangVioBaseModel;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviom.FenQiXiangVioConfigEntity;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviom.DlFenQiXiangVioModel;
import com.haojieopdzfgvaewt.bbsdgrgdz.mvp.XActivity;
import com.haojieopdzfgvaewt.bbsdgrgdz.net.ApiSubscriber;
import com.haojieopdzfgvaewt.bbsdgrgdz.net.NetError;
import com.haojieopdzfgvaewt.bbsdgrgdz.net.XApi;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou.MyFenQiXiangVioToast;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou.FenQiXiangVioOpenUtil;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou.StatusBarUtilFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviow.FenQiXiangVioClickTextView;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviow.CountDownTimerFenQiXiangVio;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlFenQiXiangVioActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private FenQiXiangVioClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilFenQiXiangVio.setTransparent(this, false);
        if (PreferencesOpenUtilFenQiXiangVio.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_fen_xiang_qi_vio, null));
        getConfig();
        readTv.setText(FenQiXiangVioOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpApiFenQiXiangVio.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApiFenQiXiangVio.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            FenQiXiangVioOpenUtil.jumpPage(DlFenQiXiangVioActivity.this, JumpH5FenQiXiangVioActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyFenQiXiangVioToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyFenQiXiangVioToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyFenQiXiangVioToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyFenQiXiangVioToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(MainFenQiXiangVioApp.getInstance());
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
        return R.layout.activity_fen_xiang_qi_vio_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpApiFenQiXiangVio.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            FenQiXiangVioOpenUtil.showErrorInfo(DlFenQiXiangVioActivity.this, error);
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesOpenUtilFenQiXiangVio.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpApiFenQiXiangVio.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<DlFenQiXiangVioModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            FenQiXiangVioOpenUtil.showErrorInfo(DlFenQiXiangVioActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel<DlFenQiXiangVioModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesOpenUtilFenQiXiangVio.saveString("ip", ip);
                                    PreferencesOpenUtilFenQiXiangVio.saveString("phone", phone);
                                    PreferencesOpenUtilFenQiXiangVio.saveInt("mobileType", mobileType);
                                    FenQiXiangVioOpenUtil.jumpPage(DlFenQiXiangVioActivity.this, MainFenQiXiangVioActivity.class);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyFenQiXiangVioToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpApiFenQiXiangVio.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            FenQiXiangVioOpenUtil.showErrorInfo(DlFenQiXiangVioActivity.this, error);
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel fenQiXiangVioBaseModel) {
                            if (fenQiXiangVioBaseModel != null) {
                                if (fenQiXiangVioBaseModel.getCode() == 200) {
                                    MyFenQiXiangVioToast.showShort("验证码发送成功");
                                    CountDownTimerFenQiXiangVio cdt = new CountDownTimerFenQiXiangVio(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
