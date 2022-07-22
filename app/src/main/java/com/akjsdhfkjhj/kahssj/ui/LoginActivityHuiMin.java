package com.akjsdhfkjhj.kahssj.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.akjsdhfkjhj.kahssj.R;
import com.akjsdhfkjhj.kahssj.net.Api;
import com.akjsdhfkjhj.kahssj.utils.MainUtil;
import com.akjsdhfkjhj.kahssj.utils.SPUtilis;
import com.akjsdhfkjhj.kahssj.utils.StatusBarUtil;
import com.akjsdhfkjhj.kahssj.utils.ToastUtil;
import com.akjsdhfkjhj.kahssj.mvp.XActivity;
import com.akjsdhfkjhj.kahssj.router.Router;
import com.akjsdhfkjhj.kahssj.widget.SpanTextView;
import com.lihang.ShadowLayout;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.akjsdhfkjhj.kahssj.ActivityCollector;

import com.akjsdhfkjhj.kahssj.present.DlPresent;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivityHuiMin extends XActivity<DlPresent> {

    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.remind_tv)
    SpanTextView remindTv;
    @BindView(R.id.remind_cb)
    public CheckBox remindCb;
    @BindView(R.id.rotate_loading)
    public RotateLoading rotateLoading;
    @BindView(R.id.loading_fl)
    public View loadingFl;
    @BindView(R.id.verification_ll)
    public View verificationLl;
    @BindView(R.id.login_sl)
    View loginSl;

    private String mobileStr, verificationStr, ip = "";
    private Bundle bundle;
    public boolean isNeedChecked = true, isNeedVerification = true;
    private static final String key = "1234567890123456";
    private static final String iv = "1234567890123456";

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        if (SPUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        initListener();
        getP().getGankData();
        getIp();
        remindTv.setText(createSpanTexts(), position -> {
            switch (position){
                case 1:
                    if (!TextUtils.isEmpty(SPUtilis.getStringFromPref("AGREEMENT"))) {
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", SPUtilis.getStringFromPref("AGREEMENT") + Api.PRIVACY_POLICY);
                        MainUtil.getValue(LoginActivityHuiMin.this, WebActivity.class, bundle);
                    }
                    break;
                default:
                    if (!TextUtils.isEmpty(SPUtilis.getStringFromPref("AGREEMENT"))) {
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", SPUtilis.getStringFromPref("AGREEMENT") + Api.USER_SERVICE_AGREEMENT);
                        MainUtil.getValue(LoginActivityHuiMin.this, WebActivity.class, bundle);
                    }
                    break;
            }
        });
    }

    /**
     * 加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        return encrypt(data, key, iv);
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public static String desEncrypt(String data) {
        return desEncrypt(data, key, iv);
    }

    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param key  加密key
     * @param iv   加密iv
     * @return 加密的结果
     * @throws Exception
     */
    private static String encrypt(String data, String key, String iv) {
        try {
            //"算法/模式/补码方式"NoPadding PkcsPadding
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void getIp() {
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
                    formatJson(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return px
     */
    public static int sp2px(float spValue) {
        return (int) (0.5f + spValue * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 解密方法
     *
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv   解密iv
     * @return 解密的结果
     * @throws Exception
     */
    private static String desEncrypt(String data, String key, String iv) {
        try {
            byte[] encrypted1 = Base64.decode(data, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String password = "123456";
        String key = "1234567890123456";
        String iv = "1234567890123456";
        String encrypt = encrypt(password, key, iv);
        System.out.println(encrypt);

        System.out.println(desEncrypt("QEwd/DWmy/4yGncCqBofQQ==", key, iv));
    }

    @Override
    public DlPresent newP() {
        return new DlPresent();
    }

    private void formatJson(String jsonData) {
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

    private void initListener() {
        loginSl.setOnClickListener(v -> {
            getIp();
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (mobileStr.isEmpty()) {
                ToastUtil.showShort("请输入手机号");
                return;
            }
            if (!MainUtil.isValidPhoneNumber(mobileStr)) {
                ToastUtil.showShort("请输入正确的手机号");
                return;
            }
            if (verificationStr.isEmpty() && isNeedVerification) {
                ToastUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            getP().login(mobileStr, verificationStr, ip);

        });
        getVerificationTv.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (mobileStr.isEmpty()) {
                ToastUtil.showShort("请输入手机号");
                return;
            }
            if (!MainUtil.isValidPhoneNumber(mobileStr)) {
                ToastUtil.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(mobileStr, getVerificationTv);
        });

    }

    private List<SpanTextView.BaseSpanModel> createSpanTexts() {
        List<SpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextView.ClickSpanModel spanModel = new SpanTextView.ClickSpanModel();
        SpanTextView.TextSpanModel textSpanModel = new SpanTextView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
