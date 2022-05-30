package com.rihdkauecgh.plihgnytrvfws.ui;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.ui.WebActivity;
import com.rihdkauecgh.plihgnytrvfws.router.Router;

import butterknife.BindView;
import butterknife.OnClick;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;

/**
 * Created by wanglei on 2016/12/31.
 */

public class AboutActivity extends XActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void initData(Bundle savedInstanceState) {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("关于XDroidMvp");
    }

    @OnClick({
            R.id.tv_githubMvc,
            R.id.tv_githubMvp
    })
    public void clickEvent(View view) {
        switch (view.getId()) {

            case R.id.tv_githubMvc:
                WebActivity.launch(context, "https://github.com/limedroid/XDroid", "XDroid");
                break;

            case R.id.tv_githubMvp:
                WebActivity.launch(context, "https://github.com/limedroid/XDroidMvp", "XDroid");
                break;
        }
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(AboutActivity.class)
                .data(new Bundle())
                .launch();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
