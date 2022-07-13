package com.lpsydsnl.jtxqchbb.page;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lpsydsnl.jtxqchbb.net.HttpMeiJieApi;
import com.lpsydsnl.jtxqchbb.R;
import com.lpsydsnl.jtxqchbb.model.MeiJieBaseModel;
import com.lpsydsnl.jtxqchbb.model.ConfigMeiJieEntity;
import com.lpsydsnl.jtxqchbb.model.DlModelMeiJie;
import com.lpsydsnl.jtxqchbb.mvp.XActivity;
import com.lpsydsnl.jtxqchbb.net.ApiSubscriber;
import com.lpsydsnl.jtxqchbb.net.NetError;
import com.lpsydsnl.jtxqchbb.net.XApi;
import com.lpsydsnl.jtxqchbb.use.MyToastMeiJie;
import com.lpsydsnl.jtxqchbb.use.OpenMeiJieUtil;
import com.lpsydsnl.jtxqchbb.use.MeiJiePreferencesOpenUtil;
import com.lpsydsnl.jtxqchbb.use.StatusMeiJieBarUtil;
import com.lpsydsnl.jtxqchbb.view.ClickTextViewMeiJie;
import com.lpsydsnl.jtxqchbb.view.CountMeiJieDownTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlMeiJieActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewMeiJie readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean isConnected(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusMeiJieBarUtil.setTransparent(this, false);
        if (MeiJiePreferencesOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_meijie, null));
        new Handler().postDelayed(() -> {
            getConfig();
        }, 200);
        readTv.setText(OpenMeiJieUtil.createDlSpanTexts(), position -> {
            if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("AGREEMENT"))) {
                bundle = new Bundle();
                if (position == 1) {
                    bundle.putString("url", MeiJiePreferencesOpenUtil.getString("AGREEMENT") + HttpMeiJieApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                } else {
                    bundle.putString("url", MeiJiePreferencesOpenUtil.getString("AGREEMENT") + HttpMeiJieApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                }
                OpenMeiJieUtil.getValue(DlMeiJieActivity.this, MeiJieJumpH5Activity.class, bundle);
            }
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastMeiJie.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastMeiJie.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyToastMeiJie.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyToastMeiJie.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dl_meijie;
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean ytujngh(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean ergdfb(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"))) {
            HttpMeiJieApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<MeiJieBaseModel<ConfigMeiJieEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenMeiJieUtil.showErrorInfo(DlMeiJieActivity.this, error);
                        }

                        @Override
                        public void onNext(MeiJieBaseModel<ConfigMeiJieEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    MeiJiePreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean nghjynf(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean vdfgert(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
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

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean myumgdh(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean vfdvertgsdf(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public void login(String phone, String verificationStr) {
        if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            HttpMeiJieApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MeiJieBaseModel<DlModelMeiJie>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenMeiJieUtil.showErrorInfo(DlMeiJieActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(MeiJieBaseModel<DlModelMeiJie> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    OpenMeiJieUtil.getValue(DlMeiJieActivity.this, MainActivityMeiJie.class, null, true);
                                    int mobileType = dlModel.getData().getMobileType();
                                    MeiJiePreferencesOpenUtil.saveString("ip", ip);
                                    MeiJiePreferencesOpenUtil.saveString("phone", phone);
                                    MeiJiePreferencesOpenUtil.saveInt("mobileType", mobileType);
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyToastMeiJie.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean nyjutdh(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean vcsddfwef(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"))) {
            HttpMeiJieApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MeiJieBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenMeiJieUtil.showErrorInfo(DlMeiJieActivity.this, error);
                        }

                        @Override
                        public void onNext(MeiJieBaseModel meiJieBaseModel) {
                            if (meiJieBaseModel != null) {
                                if (meiJieBaseModel.getCode() == 200) {
                                    MyToastMeiJie.showShort("验证码发送成功");
                                    CountMeiJieDownTimer cdt = new CountMeiJieDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean utyujdh(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean isConnvsdfewrected(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
