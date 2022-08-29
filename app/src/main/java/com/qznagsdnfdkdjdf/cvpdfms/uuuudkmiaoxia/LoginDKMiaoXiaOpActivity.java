package com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.qznagsdnfdkdjdf.cvpdfms.R;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.DKMiaoXiaOpStaticUtil;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.StatusDKMiaoXiaOpBarUtil;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.ToastUtilDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XActivity;
import com.qznagsdnfdkdjdf.cvpdfms.router.Router;
import com.qznagsdnfdkdjdf.cvpdfms.wwwwdkmiaoxia.DKMiaoXiaOpSpanTextView;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.qznagsdnfdkdjdf.cvpdfms.ActivityCollector;

import com.qznagsdnfdkdjdf.cvpdfms.ppppdkmiaoxia.LoginDKMiaoXiaOpPresent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginDKMiaoXiaOpActivity extends XActivity<LoginDKMiaoXiaOpPresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    DKMiaoXiaOpSpanTextView loginRemindTv;
    @BindView(R.id.remind_cb)
    public CheckBox remindCb;
    @BindView(R.id.rotate_loading)
    public RotateLoading rotateLoading;
    @BindView(R.id.loading_fl)
    public View loadingFl;
    @BindView(R.id.verification_ll)
    public View verificationLl;

    private String phoneStr, verificationStr, ip = "";
    private Bundle bundle;
    public boolean isNeedChecked = true, isNeedVerification = true;

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (SharedPreferencesUtilisDKMiaoXiaOp.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusDKMiaoXiaOpBarUtil.setTransparent(this, false);
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
                if (position == 1) {
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", ApiDKMiaoXiaOp.PRIVACY_POLICY);
                    Router.newIntent(LoginDKMiaoXiaOpActivity.this)
                            .to(DKMiaoXiaOpWebViewActivity.class)
                            .data(bundle)
                            .launch();
                } else {
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", ApiDKMiaoXiaOp.USER_SERVICE_AGREEMENT);
                    Router.newIntent(LoginDKMiaoXiaOpActivity.this)
                            .to(DKMiaoXiaOpWebViewActivity.class)
                            .data(bundle)
                            .launch();
                }
        });
    }

    public static String wergfdg(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mhxfdh(Object o) {

        return toDouble(o, 0);
    }

    public static double utghj(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long bfdhtsy(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://pv.sohu.com/cityjson")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            ip = jsonObject.getString("cip");//取得其名字的值，一般是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ouyikhfg(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double trzgf(Object o) {

        return toDouble(o, 0);
    }

    public static double mfhfxh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long ngfhty(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private void initListener() {
        loginBtn.setOnClickListener(v -> {
            sendRequestWithOkHttp();
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtilDKMiaoXiaOp.showShort("请输入手机号");
                return;
            }
            if (!DKMiaoXiaOpStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilDKMiaoXiaOp.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilDKMiaoXiaOp.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtilDKMiaoXiaOp.showShort("请阅读用户协议及隐私政策");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            getP().login(phoneStr, verificationStr, ip);

        });
        getVerificationTv.setOnClickListener(v -> {
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtilDKMiaoXiaOp.showShort("请输入手机号");
                return;
            }
            if (!DKMiaoXiaOpStaticUtil.isValidPhoneNumber(phoneStr)) {
                ToastUtilDKMiaoXiaOp.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    public static String erdfzx(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double jkfgsdf(Object o) {

        return toDouble(o, 0);
    }

    public static double rewths(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long ndshfdh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_dk_miao_xia_op_new;
    }

    @Override
    public LoginDKMiaoXiaOpPresent newP() {
        return new LoginDKMiaoXiaOpPresent();
    }

    private List<DKMiaoXiaOpSpanTextView.BaseSpanModel> createSpanTexts() {
        List<DKMiaoXiaOpSpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        DKMiaoXiaOpSpanTextView.ClickSpanModel spanModel = new DKMiaoXiaOpSpanTextView.ClickSpanModel();
        DKMiaoXiaOpSpanTextView.TextSpanModel textSpanModel = new DKMiaoXiaOpSpanTextView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new DKMiaoXiaOpSpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }

    public static String rtfdgsd(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mgfnsfg(Object o) {

        return toDouble(o, 0);
    }

    public static double ewrgfdsg(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long ngzfgad(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
