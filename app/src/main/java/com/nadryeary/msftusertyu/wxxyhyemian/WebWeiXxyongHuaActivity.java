package com.nadryeary.msftusertyu.wxxyhyemian;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.nadryeary.msftusertyu.R;
import com.nadryeary.msftusertyu.mvp.XActivity;
import com.nadryeary.msftusertyu.net.ApiSubscriber;
import com.nadryeary.msftusertyu.net.NetError;
import com.nadryeary.msftusertyu.net.XApi;
import com.nadryeary.msftusertyu.wxxyhgongju.DownloadApkUtilWeiXxyongHua;
import com.nadryeary.msftusertyu.wxxyhgongju.PreferencesWeiXxyongHuaOpenUtil;
import com.nadryeary.msftusertyu.wxxyhgongju.StatusBarWeiXxyongHuaUtil;
import com.nadryeary.msftusertyu.wxxyhjiekou.WeiXxyongHuaApi;
import com.nadryeary.msftusertyu.wxxyhshiti.BaseWeiXxyongHuaModel;
import com.nadryeary.msftusertyu.wxxyhshiti.ConfigWeiXxyongHuaEntity;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

public class WebWeiXxyongHuaActivity extends XActivity{

    @BindView(R.id.biaoti_tv)
    TextView biaotiTv;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.h5_view)
    WebView webView;

    private Bundle bundle;
    private String webUrl, biaoti;

    @Override
    public int getLayoutId() {
        return R.layout.j_wxxyh_activity_jump_h5;
    }

    /**
            * 获取当前手机系统语言。
            *
            * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
            */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }


    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarWeiXxyongHuaUtil.setTransparent(this, false);
        bundle = getIntent().getExtras();
        if (bundle.containsKey("biaoti"))
            biaoti = bundle.getString("biaoti");
        if (bundle.containsKey("url"))
            webUrl = bundle.getString("url");
        biaotiTv.setText(biaoti);
        backImage.setOnClickListener(v -> {
            finish();
        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);

        webView.loadUrl(webUrl);
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
     * Activity.
     * <p>
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the {@link android.view.Surface} of
     * the Activity behind to be released.
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        } catch (Throwable t) {
        }
    }

    public void getValue() {
        WeiXxyongHuaApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseWeiXxyongHuaModel<ConfigWeiXxyongHuaEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseWeiXxyongHuaModel<ConfigWeiXxyongHuaEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesWeiXxyongHuaOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesWeiXxyongHuaOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }


}
