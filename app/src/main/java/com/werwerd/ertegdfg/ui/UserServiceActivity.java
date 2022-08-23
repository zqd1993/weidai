package com.werwerd.ertegdfg.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.model.BaseRespYouXinModel;
import com.werwerd.ertegdfg.model.ConfigYouXinModel;
import com.werwerd.ertegdfg.mvp.XActivity;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.net.ApiSubscriber;
import com.werwerd.ertegdfg.net.NetError;
import com.werwerd.ertegdfg.net.XApi;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StatusBarYouXinUtil;

import butterknife.BindView;

public class UserServiceActivity extends XActivity {

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
        StatusBarYouXinUtil.setTransparent(this, false);
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
                .subscribe(new ApiSubscriber<BaseRespYouXinModel<ConfigYouXinModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespYouXinModel<ConfigYouXinModel> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SharedPreferencesYouXinUtilis.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (SharedPreferencesYouXinUtilis.getBoolFromPref("NO_RECORD")) {
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
