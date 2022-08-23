package com.rihdkauecgh.plihgnytrvfws.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.net.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.net.NetError;
import com.rihdkauecgh.plihgnytrvfws.net.XApi;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StatusBarUtil;

import butterknife.BindView;

public class UserServiceAgreementActivity extends XActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.title_tv)
    TextView tvTitle;
    @BindView(R.id.back_img)
    ImageView imgBack;

    private Bundle bundle;

    private int tag;

    private String url, title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        bundle = getIntent().getExtras();
        if (bundle.containsKey("tag"))
            tag = bundle.getInt("tag");
        if (bundle.containsKey("url"))
            url = bundle.getString("url");
        if (bundle.containsKey("title"))
            title = bundle.getString("title");
        if (tag == 1) {
            tvTitle.setText(getResources().getString(R.string.privacy_policy));
        } else if (tag == 2) {
            tvTitle.setText(getResources().getString(R.string.user_service_agreement));
        } else {
            tvTitle.setText(title);
        }
        imgBack.setOnClickListener(v -> {
            finish();
        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);

        webView.loadUrl(url);
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
        Api.getGankService().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<ConfigModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModel<ConfigModel> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SharedPreferencesUtilis.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (SharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
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
