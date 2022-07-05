package com.wolai.dai.yemian;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wolai.dai.jiekou.JiXinApi;
import com.wolai.dai.R;
import com.wolai.dai.kongjian.JiXinClickTextView;
import com.wolai.dai.shiti.JixinBaseModel;
import com.wolai.dai.shiti.JixinConfigEntity;
import com.wolai.dai.shiti.JixinDlModel;
import com.wolai.dai.mvp.XActivity;
import com.wolai.dai.net.ApiSubscriber;
import com.wolai.dai.net.NetError;
import com.wolai.dai.net.XApi;
import com.wolai.dai.gongju.JiXinMyToast;
import com.wolai.dai.gongju.JiXinOpenUtil;
import com.wolai.dai.gongju.JiXinPreferencesOpenUtil;
import com.wolai.dai.gongju.JiXinStatusBarUtil;
import com.wolai.dai.kongjian.JiXinCountDownTimer;

import org.json.JSONObject;

import java.math.BigDecimal;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JixinDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private JiXinClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiXinStatusBarUtil.setTransparent(this, false);
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.jixin_view_loading, null));
        getConfig();
        readTv.setText(JiXinOpenUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", JiXinApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.yryvb));
            } else {
                bundle.putString("url", JiXinApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.retert));
            }
            JiXinOpenUtil.jumpPage(JixinDlActivity.this, JixinJumpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                JiXinMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                JiXinMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                JiXinMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()){
                JiXinMyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr,yzmStr);
        });
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.jixin_activity_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            JiXinApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel<JixinConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            JiXinOpenUtil.showErrorInfo(JixinDlActivity.this, error);
                        }

                        @Override
                        public void onNext(JixinBaseModel<JixinConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    JiXinPreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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

    public static String formatNum(String num) {
        return formatNum(num, false);
    }

    public static String formatNum(String num, Boolean kBool) {
        StringBuffer sb = new StringBuffer();
        if (kBool == null)
            kBool = false;

        BigDecimal b0 = new BigDecimal("1000");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String nuit = "";

        // 以千为单位处理
        if (kBool) {
            if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
                return "999+";
            }
            return num;
        }

        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            sb.append(b3.toString());
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            formatNumStr = b3.divide(b1).toString();
            nuit = "万";
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            formatNumStr = b3.divide(b2).toString();
            nuit = "亿";
        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(nuit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(nuit);
                }
            }
        }
        if (sb.length() == 0)
            return "0";
        return sb.toString();
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
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            JiXinApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel<JixinDlModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            JiXinOpenUtil.showErrorInfo(JixinDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(JixinBaseModel<JixinDlModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    JiXinOpenUtil.jumpPage(JixinDlActivity.this, JixinMainActivity.class);
                                    int mobileType = dlModel.getData().getMobileType();
                                    JiXinPreferencesOpenUtil.saveString("ip", ip);
                                    JiXinPreferencesOpenUtil.saveString("phone", phone);
                                    JiXinPreferencesOpenUtil.saveInt("mobileType", mobileType);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    JiXinMyToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            JiXinApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            JiXinOpenUtil.showErrorInfo(JixinDlActivity.this, error);
                        }

                        @Override
                        public void onNext(JixinBaseModel baseModel) {
                            if (baseModel != null) {
                                if (baseModel.getCode() == 200) {
                                    JiXinMyToast.showShort("验证码发送成功");
                                    JiXinCountDownTimer cdt = new JiXinCountDownTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }
}
