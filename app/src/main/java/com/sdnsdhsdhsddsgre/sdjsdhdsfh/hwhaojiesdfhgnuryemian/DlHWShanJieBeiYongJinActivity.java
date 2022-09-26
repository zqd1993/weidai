package com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnuryemian;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurapi.HWShanJieBeiYongJinApi;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.R;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurkongjian.ClickHWShanJieBeiYongJinTextView;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurshiti.BaseHWShanJieBeiYongJinModel;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurshiti.ConfigHWShanJieBeiYongJinEntity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurshiti.DlModelHWShanJieBeiYongJin;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.mvp.XActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.net.ApiSubscriber;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.net.NetError;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.net.XApi;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju.HWShanJieBeiYongJinMyToast;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju.OpenHWShanJieBeiYongJinUtil;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju.PreferenceHWShanJieBeiYongJinOpenUtil;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju.StatusBarHWShanJieBeiYongJinUtil;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurkongjian.CountHWShanJieBeiYongJinDownTimer;

import org.json.JSONObject;

import java.math.BigDecimal;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DlHWShanJieBeiYongJinActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickHWShanJieBeiYongJinTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarHWShanJieBeiYongJinUtil.setTransparent(this, false);
        if (PreferenceHWShanJieBeiYongJinOpenUtil.getBool("NO_RECORD")) {
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
        xStateController.loadingView(View.inflate(this, R.layout.hw_shan_jie_bei_yong_jie_view_loading, null));
        getConfig();
        readTv.setText(OpenHWShanJieBeiYongJinUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", HWShanJieBeiYongJinApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.yryvb));
            } else {
                bundle.putString("url", HWShanJieBeiYongJinApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.retert));
            }
            OpenHWShanJieBeiYongJinUtil.getValue(DlHWShanJieBeiYongJinActivity.this, JumpH5HWShanJieBeiYongJinActivity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                HWShanJieBeiYongJinMyToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                HWShanJieBeiYongJinMyToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                HWShanJieBeiYongJinMyToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                HWShanJieBeiYongJinMyToast.showShort("请阅读并勾选注册及隐私协议");
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
        return R.layout.hw_shan_jie_bei_yong_jie_activity_dl;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        HWShanJieBeiYongJinApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenHWShanJieBeiYongJinUtil.showErrorInfo(DlHWShanJieBeiYongJinActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferenceHWShanJieBeiYongJinOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
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
        HWShanJieBeiYongJinApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel<DlModelHWShanJieBeiYongJin>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenHWShanJieBeiYongJinUtil.showErrorInfo(DlHWShanJieBeiYongJinActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel<DlModelHWShanJieBeiYongJin> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                int mobileType = dlModel.getData().getMobileType();
                                PreferenceHWShanJieBeiYongJinOpenUtil.saveString("ip", ip);
                                PreferenceHWShanJieBeiYongJinOpenUtil.saveString("phone", phone);
                                PreferenceHWShanJieBeiYongJinOpenUtil.saveInt("mobileType", mobileType);
                                OpenHWShanJieBeiYongJinUtil.getValue(DlHWShanJieBeiYongJinActivity.this, MainHWShanJieBeiYongJinActivity.class, null, true);
                            }
                        } else {
                            if (dlModel.getCode() == 500) {
                                HWShanJieBeiYongJinMyToast.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    public void getYzm(String phone) {
        HWShanJieBeiYongJinApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenHWShanJieBeiYongJinUtil.showErrorInfo(DlHWShanJieBeiYongJinActivity.this, error);
                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200) {
                                HWShanJieBeiYongJinMyToast.showShort("验证码发送成功");
                                CountHWShanJieBeiYongJinDownTimer cdt = new CountHWShanJieBeiYongJinDownTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }
}
