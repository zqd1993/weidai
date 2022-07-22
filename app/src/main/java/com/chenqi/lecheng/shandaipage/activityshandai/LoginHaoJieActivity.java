package com.chenqi.lecheng.shandaipage.activityshandai;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.shadnaihttp.ApiHaoJie;
import com.chenqi.lecheng.utilsshandai.SharedPreferencesHaoJieUtilis;
import com.chenqi.lecheng.utilsshandai.StaticHaoJieUtil;
import com.chenqi.lecheng.utilsshandai.StatusBarHaoJieUtil;
import com.chenqi.lecheng.utilsshandai.ToastHaoJieUtil;
import com.chenqi.lecheng.mvp.XActivity;
import com.chenqi.lecheng.router.Router;
import com.google.android.material.snackbar.Snackbar;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.chenqi.lecheng.ActivityCollector;

import com.chenqi.lecheng.presentshandai.LoginHaoJiePresent;
import com.chenqi.lecheng.widgetshandai.SpanTextHaoJieView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginHaoJieActivity extends XActivity<LoginHaoJiePresent> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextHaoJieView loginRemindTv;
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


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarHaoJieUtil.setTransparent(this, false);
        if (SharedPreferencesHaoJieUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        initListener();
        new Handler().postDelayed(() -> {
            getP().getConfig();
        }, 200);
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (!TextUtils.isEmpty(SharedPreferencesHaoJieUtilis.getStringFromPref("AGREEMENT"))) {
                if (position == 1) {
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", SharedPreferencesHaoJieUtilis.getStringFromPref("AGREEMENT") + ApiHaoJie.PRIVACY_POLICY);
                    StaticHaoJieUtil.getValue(LoginHaoJieActivity.this, HaoJieWebActivity.class, bundle);
                } else {
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", SharedPreferencesHaoJieUtilis.getStringFromPref("AGREEMENT") + ApiHaoJie.USER_SERVICE_AGREEMENT);
                    StaticHaoJieUtil.getValue(LoginHaoJieActivity.this, HaoJieWebActivity.class, bundle);
                }
            }
        });
    }


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void vxvsdg(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void yutrhfgs(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void wervbadf(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
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


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void werdfbdf(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void ityugfj(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void ertbdfgs(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

    private void initListener() {
        loginBtn.setOnClickListener(v -> {
            sendRequestWithOkHttp();
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastHaoJieUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticHaoJieUtil.isValidPhoneNumber(phoneStr)) {
                ToastHaoJieUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastHaoJieUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastHaoJieUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            getP().dl(phoneStr, verificationStr, ip);

        });
        getVerificationTv.setOnClickListener(v -> {
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastHaoJieUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticHaoJieUtil.isValidPhoneNumber(phoneStr)) {
                ToastHaoJieUtil.showShort("请输入正确的手机号");
                return;
            }
            getP().getVerifyCode(phoneStr, getVerificationTv);
        });

    }


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void werfsdA(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void utyeusdr(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void bsdfgtr(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_haojie;
    }

    @Override
    public LoginHaoJiePresent newP() {
        return new LoginHaoJiePresent();
    }

    private List<SpanTextHaoJieView.BaseSpanModel> createSpanTexts() {
        List<SpanTextHaoJieView.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextHaoJieView.ClickSpanModel spanModel = new SpanTextHaoJieView.ClickSpanModel();
        SpanTextHaoJieView.TextSpanModel textSpanModel = new SpanTextHaoJieView.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextHaoJieView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void zxcsdf(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void yurerg(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void sdfsdgfer(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void oyuiy(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void wefcvdxv(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void wsrthxfg(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }
}
