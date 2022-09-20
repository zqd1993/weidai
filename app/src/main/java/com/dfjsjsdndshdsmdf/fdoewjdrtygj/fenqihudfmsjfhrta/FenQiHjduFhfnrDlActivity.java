package com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta;

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

import com.dfjsjsdndshdsmdf.fdoewjdrtygj.FenQiHjduFhfnrMainApp;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtapi.HttpFenQiHjduFhfnrApi;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.R;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.FenQiHjduFhfnrBaseModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.ConfigFenQiHjduFhfnrEntity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.FenQiHjduFhfnrDlModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.mvp.XActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.ApiSubscriber;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.NetError;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.XApi;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.FenQiHjduFhfnrMyToast;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.OpeFenQiHjduFhfnrUti;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.PreferencFenQiHjduFhfnrOpenUtil;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.FenQiHjduFhfnrStatusBarUtil;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtw.ClickTextViewFenQiHjduFhfnr;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtw.CountDownTimerFenQiHjduFhfnr;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;

import org.json.JSONObject;

import java.io.IOException;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FenQiHjduFhfnrDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextViewFenQiHjduFhfnr readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "", oaidStr;
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true, isOaid;

    @Override
    public void initData(Bundle savedInstanceState) {
        FenQiHjduFhfnrStatusBarUtil.setTransparent(this, false);
        if (PreferencFenQiHjduFhfnrOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_fen_qas_han_jnfe, null));
        getConfig();
        readTv.setText(OpeFenQiHjduFhfnrUti.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HttpFenQiHjduFhfnrApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", HttpFenQiHjduFhfnrApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpeFenQiHjduFhfnrUti.getValue(FenQiHjduFhfnrDlActivity.this, JumpH5FenQiHjduFhfnrActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                FenQiHjduFhfnrMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                FenQiHjduFhfnrMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                FenQiHjduFhfnrMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                FenQiHjduFhfnrMyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(FenQiHjduFhfnrMainApp.getInstance());
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
        return R.layout.activity_fen_qas_han_jnfe_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
            HttpFenQiHjduFhfnrApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel<ConfigFenQiHjduFhfnrEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeFenQiHjduFhfnrUti.showErrorInfo(FenQiHjduFhfnrDlActivity.this, error);
                        }

                        @Override
                        public void onNext(FenQiHjduFhfnrBaseModel<ConfigFenQiHjduFhfnrEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencFenQiHjduFhfnrOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
            HttpFenQiHjduFhfnrApi.getInterfaceUtils().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel<FenQiHjduFhfnrDlModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeFenQiHjduFhfnrUti.showErrorInfo(FenQiHjduFhfnrDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(FenQiHjduFhfnrBaseModel<FenQiHjduFhfnrDlModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencFenQiHjduFhfnrOpenUtil.saveString("ip", ip);
                                    PreferencFenQiHjduFhfnrOpenUtil.saveString("phone", phone);
                                    PreferencFenQiHjduFhfnrOpenUtil.saveInt("mobileType", mobileType);
                                    OpeFenQiHjduFhfnrUti.getValue(FenQiHjduFhfnrDlActivity.this, MainFenQiHjduFhfnrActivity.class, null, true);
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    FenQiHjduFhfnrMyToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
    }

    public void getYzm(String phone) {
            HttpFenQiHjduFhfnrApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeFenQiHjduFhfnrUti.showErrorInfo(FenQiHjduFhfnrDlActivity.this, error);
                        }

                        @Override
                        public void onNext(FenQiHjduFhfnrBaseModel fenQiHjduFhfnrBaseModel) {
                            if (fenQiHjduFhfnrBaseModel != null) {
                                if (fenQiHjduFhfnrBaseModel.getCode() == 200) {
                                    FenQiHjduFhfnrMyToast.showShort("验证码发送成功");
                                    CountDownTimerFenQiHjduFhfnr cdt = new CountDownTimerFenQiHjduFhfnr(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
    }
}
