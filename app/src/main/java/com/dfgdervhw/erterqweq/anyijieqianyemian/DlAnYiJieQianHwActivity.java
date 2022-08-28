package com.dfgdervhw.erterqweq.anyijieqianyemian;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dfgdervhw.erterqweq.anyijieqianjiekou.AnYiJieQianHwApi;
import com.dfgdervhw.erterqweq.R;
import com.dfgdervhw.erterqweq.anyijieqiankongjian.ClickAnYiJieQianHwTextView;
import com.dfgdervhw.erterqweq.anyijieqianshiti.BaseAnYiJieQianHwModel;
import com.dfgdervhw.erterqweq.anyijieqianshiti.ConfigAnYiJieQianHwEntity;
import com.dfgdervhw.erterqweq.anyijieqianshiti.DlModelAnYiJieQianHw;
import com.dfgdervhw.erterqweq.mvp.XActivity;
import com.dfgdervhw.erterqweq.net.ApiSubscriber;
import com.dfgdervhw.erterqweq.net.NetError;
import com.dfgdervhw.erterqweq.net.XApi;
import com.dfgdervhw.erterqweq.anyijieqiangongju.AnYiJieQianHwMyToast;
import com.dfgdervhw.erterqweq.anyijieqiangongju.OpenAnYiJieQianHwUtil;
import com.dfgdervhw.erterqweq.anyijieqiangongju.PreferencesAnYiJieQianHwOpenUtil;
import com.dfgdervhw.erterqweq.anyijieqiangongju.StatusBarAnYiJieQianHwUtil;
import com.dfgdervhw.erterqweq.anyijieqiankongjian.CountAnYiJieQianHwDownTimer;

import org.json.JSONObject;

import java.math.BigDecimal;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlAnYiJieQianHwActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickAnYiJieQianHwTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarAnYiJieQianHwUtil.setTransparent(this, false);
        if (PreferencesAnYiJieQianHwOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.an_yi_jie_qian_view_loading, null));
        getConfig();
        readTv.setText(OpenAnYiJieQianHwUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", AnYiJieQianHwApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.yryvb));
            } else {
                bundle.putString("url", AnYiJieQianHwApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.retert));
            }
            OpenAnYiJieQianHwUtil.getValue(DlAnYiJieQianHwActivity.this, JumpH5AnYiJieQianHwActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                AnYiJieQianHwMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                AnYiJieQianHwMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                AnYiJieQianHwMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                AnYiJieQianHwMyToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
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
        return R.layout.an_yi_jie_qian_activity_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        AnYiJieQianHwApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel<ConfigAnYiJieQianHwEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenAnYiJieQianHwUtil.showErrorInfo(DlAnYiJieQianHwActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel<ConfigAnYiJieQianHwEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesAnYiJieQianHwOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
        if (xStateController != null)
            xStateController.showLoading();
        AnYiJieQianHwApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel<DlModelAnYiJieQianHw>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenAnYiJieQianHwUtil.showErrorInfo(DlAnYiJieQianHwActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel<DlModelAnYiJieQianHw> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                int mobileType = dlModel.getData().getMobileType();
                                PreferencesAnYiJieQianHwOpenUtil.saveString("ip", ip);
                                PreferencesAnYiJieQianHwOpenUtil.saveString("phone", phone);
                                PreferencesAnYiJieQianHwOpenUtil.saveInt("mobileType", mobileType);
                                OpenAnYiJieQianHwUtil.getValue(DlAnYiJieQianHwActivity.this, MainAnYiJieQianHwActivity.class, null, true);
                            }
                        } else {
                            if (dlModel.getCode() == 500) {
                                AnYiJieQianHwMyToast.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    public void getYzm(String phone) {
        AnYiJieQianHwApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenAnYiJieQianHwUtil.showErrorInfo(DlAnYiJieQianHwActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200) {
                                AnYiJieQianHwMyToast.showShort("验证码发送成功");
                                CountAnYiJieQianHwDownTimer cdt = new CountAnYiJieQianHwDownTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }
}
