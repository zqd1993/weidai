package com.biofs.lqgdkic.weifenqijietiaoactivity;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.biofs.lqgdkic.weifenqijietiaoapi.WeiFenQiJieTiaoApi;
import com.biofs.lqgdkic.R;
import com.biofs.lqgdkic.weifenqijietiaomodel.BaseModelWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaomodel.WeiFenQiJieTiaoConfigEntity;
import com.biofs.lqgdkic.weifenqijietiaomodel.WeiFenQiJieTiaoModel;
import com.biofs.lqgdkic.mvp.XActivity;
import com.biofs.lqgdkic.net.ApiSubscriber;
import com.biofs.lqgdkic.net.NetError;
import com.biofs.lqgdkic.net.XApi;
import com.biofs.lqgdkic.weifenqijietiaoutils.MyToastWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaoutils.OpenWeiFenQiJieTiaoUtil;
import com.biofs.lqgdkic.weifenqijietiaoutils.WeiFenQiJieTiaoPreferencesOpenUtil;
import com.biofs.lqgdkic.weifenqijietiaoutils.StatusBarUtilWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaoweidgt.ClickWeiFenQiJieTiaoTextView;
import com.biofs.lqgdkic.weifenqijietiaoweidgt.CountDownWeiFenQiJieTiaoTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlWeiFenQiJieTiaoActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickWeiFenQiJieTiaoTextView readTv;
    private View yzmCv;

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilWeiFenQiJieTiao.setTransparent(this, false);
        if (WeiFenQiJieTiaoPreferencesOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_wei_fen_qi_jie_tiao_loading, null));
        getConfig();
        readTv.setText(OpenWeiFenQiJieTiaoUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", WeiFenQiJieTiaoApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", WeiFenQiJieTiaoApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenWeiFenQiJieTiaoUtil.getValue(DlWeiFenQiJieTiaoActivity.this, JumpWeiFenQiJieTiaoH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastWeiFenQiJieTiao.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyToastWeiFenQiJieTiao.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyToastWeiFenQiJieTiao.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyToastWeiFenQiJieTiao.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wei_fen_qi_jie_tiao_dl;
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int aertrshfgh(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float reygfhgj(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float ertgdrthfg(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            WeiFenQiJieTiaoApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenWeiFenQiJieTiaoUtil.showErrorInfo(DlWeiFenQiJieTiaoActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    WeiFenQiJieTiaoPreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int aewrtgfhfgh(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float nsrthsdrrtsy(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float qwerdsfgdf(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
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
            WeiFenQiJieTiaoApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenWeiFenQiJieTiaoUtil.showErrorInfo(DlWeiFenQiJieTiaoActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    WeiFenQiJieTiaoPreferencesOpenUtil.saveString("ip", ip);
                                    WeiFenQiJieTiaoPreferencesOpenUtil.saveString("phone", phone);
                                    WeiFenQiJieTiaoPreferencesOpenUtil.saveInt("mobileType", mobileType);
                                    OpenWeiFenQiJieTiaoUtil.getValue(DlWeiFenQiJieTiaoActivity.this, WeiFenQiJieTiaoMainActivity.class, null, true);
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyToastWeiFenQiJieTiao.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int jtyughj(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float waerteryhrty(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float ersyfdggj(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public void getYzm(String phone) {
            WeiFenQiJieTiaoApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenWeiFenQiJieTiaoUtil.showErrorInfo(DlWeiFenQiJieTiaoActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseModelWeiFenQiJieTiao baseModelWeiFenQiJieTiao) {
                            if (baseModelWeiFenQiJieTiao != null) {
                                if (baseModelWeiFenQiJieTiao.getCode() == 200) {
                                    MyToastWeiFenQiJieTiao.showShort("验证码发送成功");
                                    CountDownWeiFenQiJieTiaoTimer cdt = new CountDownWeiFenQiJieTiaoTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int ertrsydrty(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float retgxfhfgh(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float ertuyrt(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
