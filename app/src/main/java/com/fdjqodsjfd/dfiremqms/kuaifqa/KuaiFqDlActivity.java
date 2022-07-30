package com.fdjqodsjfd.dfiremqms.kuaifqa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fdjqodsjfd.dfiremqms.kuaifqapi.HttpApiKuaiFq;
import com.fdjqodsjfd.dfiremqms.R;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqBaseModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.ConfigEntitKuaiFqy;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqDlModel;
import com.fdjqodsjfd.dfiremqms.mvp.XActivity;
import com.fdjqodsjfd.dfiremqms.net.ApiSubscriber;
import com.fdjqodsjfd.dfiremqms.net.NetError;
import com.fdjqodsjfd.dfiremqms.net.XApi;
import com.fdjqodsjfd.dfiremqms.kuaifqu.KuaiFqMyToast;
import com.fdjqodsjfd.dfiremqms.kuaifqu.OpeKuaiFqnUti;
import com.fdjqodsjfd.dfiremqms.kuaifqu.PreferencKuaiFqOpenUtil;
import com.fdjqodsjfd.dfiremqms.kuaifqu.KuaiFqStatusBarUtil;
import com.fdjqodsjfd.dfiremqms.kuaifqw.ClickTextViewKuaiFq;
import com.fdjqodsjfd.dfiremqms.kuaifqw.CountDownTimerKuaiFq;

import org.json.JSONObject;

import java.io.IOException;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KuaiFqDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewKuaiFq readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        KuaiFqStatusBarUtil.setTransparent(this, false);
        if (PreferencKuaiFqOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_kuai_fq, null));
        getConfig();
        readTv.setText(OpeKuaiFqnUti.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpApiKuaiFq.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpApiKuaiFq.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpeKuaiFqnUti.getValue(KuaiFqDlActivity.this, KuaiFqJumpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                KuaiFqMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                KuaiFqMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                KuaiFqMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                KuaiFqMyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    public float getRatio(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap loadFile2MemoryVersion10(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity__kuai_fq_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpApiKuaiFq.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel<ConfigEntitKuaiFqy>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeKuaiFqnUti.showErrorInfo(KuaiFqDlActivity.this, error);
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel<ConfigEntitKuaiFqy> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencKuaiFqOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpApiKuaiFq.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel<KuaiFqDlModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeKuaiFqnUti.showErrorInfo(KuaiFqDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel<KuaiFqDlModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencKuaiFqOpenUtil.saveString("ip", ip);
                                    PreferencKuaiFqOpenUtil.saveString("phone", phone);
                                    PreferencKuaiFqOpenUtil.saveInt("mobileType", mobileType);
                                    OpeKuaiFqnUti.getValue(KuaiFqDlActivity.this, MainActivityKuaiFq.class, null, true);
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    KuaiFqMyToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpApiKuaiFq.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeKuaiFqnUti.showErrorInfo(KuaiFqDlActivity.this, error);
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel kuaiFqBaseModel) {
                            if (kuaiFqBaseModel != null) {
                                if (kuaiFqBaseModel.getCode() == 200) {
                                    KuaiFqMyToast.showShort("验证码发送成功");
                                    CountDownTimerKuaiFq cdt = new CountDownTimerKuaiFq(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
