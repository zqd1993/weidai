package com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoniukaudiakuandsfwet.vsdaetgerat.apicxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewHttpApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.R;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBaseModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewConfigEntity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewDlModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.ApiSubscriber;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.NetError;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.XApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewMyToast;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewOpenUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewStatusBarUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.weidgtcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewClickTextView;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.weidgtcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewCountDownTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XiaoNiuHuaDaiKuanOpNewDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private XiaoNiuHuaDaiKuanOpNewClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        XiaoNiuHuaDaiKuanOpNewStatusBarUtil.setTransparent(this, false);
        if (XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_xiao_niu_hua_dai_kuan_op_new, null));
        getConfig();
        readTv.setText(XiaoNiuHuaDaiKuanOpNewOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", XiaoNiuHuaDaiKuanOpNewHttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", XiaoNiuHuaDaiKuanOpNewHttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue(XiaoNiuHuaDaiKuanOpNewDlActivity.this, XiaoNiuHuaDaiKuanOpNewJumpActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                XiaoNiuHuaDaiKuanOpNewMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                XiaoNiuHuaDaiKuanOpNewMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                XiaoNiuHuaDaiKuanOpNewMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                XiaoNiuHuaDaiKuanOpNewMyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xiao_niu_hua_dai_kuan_op_new_dl;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int mmtyurety(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int ertsdfyh(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int iuytjhdgjh(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            XiaoNiuHuaDaiKuanOpNewOpenUtil.showErrorInfo(XiaoNiuHuaDaiKuanOpNewDlActivity.this, error);
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int ityjhdgj(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int qweretry(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int ryeasrhgfn(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
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
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewDlModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            XiaoNiuHuaDaiKuanOpNewOpenUtil.showErrorInfo(XiaoNiuHuaDaiKuanOpNewDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewDlModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.saveString("ip", ip);
                                    XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.saveString("phone", phone);
                                    XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.saveInt("mobileType", mobileType);
                                    XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue(XiaoNiuHuaDaiKuanOpNewDlActivity.this, XiaoNiuHuaDaiKuanOpNewMainActivity.class, null, true);
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    XiaoNiuHuaDaiKuanOpNewMyToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int hhaergreh(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int wqerety(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int mrsthsert(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public void getYzm(String phone) {
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            XiaoNiuHuaDaiKuanOpNewOpenUtil.showErrorInfo(XiaoNiuHuaDaiKuanOpNewDlActivity.this, error);
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel xiaoNiuHuaDaiKuanOpNewBaseModel) {
                            if (xiaoNiuHuaDaiKuanOpNewBaseModel != null) {
                                if (xiaoNiuHuaDaiKuanOpNewBaseModel.getCode() == 200) {
                                    XiaoNiuHuaDaiKuanOpNewMyToast.showShort("验证码发送成功");
                                    XiaoNiuHuaDaiKuanOpNewCountDownTimer cdt = new XiaoNiuHuaDaiKuanOpNewCountDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
