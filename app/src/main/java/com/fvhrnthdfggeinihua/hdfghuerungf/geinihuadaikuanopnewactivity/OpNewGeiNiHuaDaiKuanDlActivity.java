package com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi.OpNewGeiNiHuaDaiKuanHttpApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.R;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBaseModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanConfigEntity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanDlModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.ApiSubscriber;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.NetError;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.XApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanMyToast;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanPreferencesOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanStatusBarUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewweidgt.OpNewGeiNiHuaDaiKuanClickTextView;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewweidgt.OpNewGeiNiHuaDaiKuanCountDownTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpNewGeiNiHuaDaiKuanDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private OpNewGeiNiHuaDaiKuanClickTextView readTv;
    private View yzmCv;

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        OpNewGeiNiHuaDaiKuanStatusBarUtil.setTransparent(this, false);
        if (OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_gei_ni_hua_dai_kuan_op_new, null));
        getConfig();
        readTv.setText(OpNewGeiNiHuaDaiKuanOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", OpNewGeiNiHuaDaiKuanHttpApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", OpNewGeiNiHuaDaiKuanHttpApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpNewGeiNiHuaDaiKuanOpenUtil.getValue(OpNewGeiNiHuaDaiKuanDlActivity.this, JumpH5ActivityJinRiYouQianHua.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                OpNewGeiNiHuaDaiKuanMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                OpNewGeiNiHuaDaiKuanMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                OpNewGeiNiHuaDaiKuanMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                OpNewGeiNiHuaDaiKuanMyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gei_ni_hua_dai_kuan_op_new_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap trhdjsrty(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap ertgdfhsrtuy(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    public void getConfig() {
        OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpNewGeiNiHuaDaiKuanOpenUtil.showErrorInfo(OpNewGeiNiHuaDaiKuanDlActivity.this, error);
                    }

                    @Override
                    public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap mktyujdrtyasery(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap rtyfghertaert(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    public void login(String phone, String verificationStr) {
        if (xStateController != null)
            xStateController.showLoading();
        OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanDlModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpNewGeiNiHuaDaiKuanOpenUtil.showErrorInfo(OpNewGeiNiHuaDaiKuanDlActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanDlModel> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                int mobileType = dlModel.getData().getMobileType();
                                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveString("ip", ip);
                                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveString("phone", phone);
                                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveInt("mobileType", mobileType);
                                OpNewGeiNiHuaDaiKuanOpenUtil.getValue(OpNewGeiNiHuaDaiKuanDlActivity.this, OpNewGeiNiHuaDaiKuanMainActivity.class, null, true);
                            }
                        } else {
                            if (dlModel.getCode() == 500) {
                                OpNewGeiNiHuaDaiKuanMyToast.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap mmsertgawetdhg(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap waertertdhsre(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    public void getYzm(String phone) {
        OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpNewGeiNiHuaDaiKuanOpenUtil.showErrorInfo(OpNewGeiNiHuaDaiKuanDlActivity.this, error);
                    }

                    @Override
                    public void onNext(OpNewGeiNiHuaDaiKuanBaseModel opNewGeiNiHuaDaiKuanBaseModel) {
                        if (opNewGeiNiHuaDaiKuanBaseModel != null) {
                            if (opNewGeiNiHuaDaiKuanBaseModel.getCode() == 200) {
                                OpNewGeiNiHuaDaiKuanMyToast.showShort("验证码发送成功");
                                OpNewGeiNiHuaDaiKuanCountDownTimer cdt = new OpNewGeiNiHuaDaiKuanCountDownTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap zbhzsrtyfguj(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap qtsryhrtu(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }
}
