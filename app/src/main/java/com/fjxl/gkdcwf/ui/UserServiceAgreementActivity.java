package com.fjxl.gkdcwf.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjxl.gkdcwf.R;
import com.fjxl.gkdcwf.bean.BaseModel;
import com.fjxl.gkdcwf.bean.ConfigEntity;
import com.fjxl.gkdcwf.gongju.KuaiJiePreferencesOpenUtil;
import com.fjxl.gkdcwf.gongju.StatusKuaiJieBarUtil;
import com.fjxl.gkdcwf.mainapi.KuaiJieApi;
import com.fjxl.gkdcwf.mvp.XActivity;
import com.fjxl.gkdcwf.net.ApiSubscriber;
import com.fjxl.gkdcwf.net.NetError;
import com.fjxl.gkdcwf.net.XApi;

import butterknife.BindView;

public class UserServiceAgreementActivity extends XActivity {

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
        return R.layout.activity_jump_h5_kuaijie;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusKuaiJieBarUtil.setTransparent(this, false);
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


    @Override
    protected void onResume() {
        super.onResume();
        getValue();
        if (webView != null) {
            webView.onResume();
        }
    }

    public void getValue() {
        KuaiJieApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<ConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModel<ConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                KuaiJiePreferencesOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (KuaiJiePreferencesOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
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

}
