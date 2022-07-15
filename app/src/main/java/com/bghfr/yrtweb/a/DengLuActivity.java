package com.bghfr.yrtweb.a;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bghfr.yrtweb.api.MyApi;
import com.bghfr.yrtweb.R;
import com.bghfr.yrtweb.m.MainModel;
import com.bghfr.yrtweb.m.SetEntity;
import com.bghfr.yrtweb.m.DengluModel;
import com.bghfr.yrtweb.mvp.XActivity;
import com.bghfr.yrtweb.net.ApiSubscriber;
import com.bghfr.yrtweb.net.NetError;
import com.bghfr.yrtweb.net.XApi;
import com.bghfr.yrtweb.u.BaseToast;
import com.bghfr.yrtweb.u.BaseUtil;
import com.bghfr.yrtweb.u.PreferencesStaticOpenUtil;
import com.bghfr.yrtweb.u.StatusBarUtil;
import com.bghfr.yrtweb.w.DianjiTextView;
import com.bghfr.yrtweb.w.DaoJiShiTimer;
import com.lihang.ShadowLayout;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DengLuActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv;
    private CheckBox remindCb;
    private DianjiTextView readTv;
    private ShadowLayout yzmCv;
    private ImageView dlBtn;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    public static Bitmap createBitmapTransparentBg(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.quanquan_loading, null));
        getConfig();
        readTv.setText(BaseUtil.createDlSpanTexts(), position -> {
            if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("AGREEMENT"))) {
                bundle = new Bundle();
                if (position == 1) {
                    bundle.putString("url", PreferencesStaticOpenUtil.getString("AGREEMENT") + MyApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                } else {
                    bundle.putString("url", PreferencesStaticOpenUtil.getString("AGREEMENT") + MyApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                }
                BaseUtil.getValue(DengLuActivity.this, WangYeActivity.class, bundle);
            }
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                BaseToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                BaseToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                BaseToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()){
                BaseToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr,yzmStr);
        });
    }

    public static byte[] createBitmapOnHideToBytes(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_denglu;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static Bitmap createBitmapWithWaterMark(View v, Bitmap watermark, int color) {
        int watermarkMargin = 12;
        int w = v.getWidth();
        int h = v.getHeight();
        int bW = watermark.getWidth();
        int bH = watermark.getHeight();
        int realW = w > bW ? w : bW;
        int realH = h + bH + watermarkMargin;
        Bitmap bmp = Bitmap.createBitmap(realW, realH, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(color);
        v.layout((realW - w) / 2, 0, (realW + w) / 2, h);
        v.draw(c);
        c.drawBitmap(watermark, (realW - bW) / 2, h, null);
        return bmp;
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
            MyApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel<SetEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            BaseUtil.showErrorInfo(DengLuActivity.this, error);
                        }

                        @Override
                        public void onNext(MainModel<SetEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesStaticOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
        if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            MyApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel<DengluModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            BaseUtil.showErrorInfo(DengLuActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(MainModel<DengluModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesStaticOpenUtil.saveString("ip", ip);
                                    PreferencesStaticOpenUtil.saveString("phone", phone);
                                    PreferencesStaticOpenUtil.saveInt("mobileType", mobileType);
                                    BaseUtil.getValue(DengLuActivity.this, ZhuYeActivity.class, null, true);
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    BaseToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
            MyApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            BaseUtil.showErrorInfo(DengLuActivity.this, error);
                        }

                        @Override
                        public void onNext(MainModel mainModel) {
                            if (mainModel != null) {
                                if (mainModel.getCode() == 200) {
                                    BaseToast.showShort("验证码发送成功");
                                    DaoJiShiTimer cdt = new DaoJiShiTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }
}
