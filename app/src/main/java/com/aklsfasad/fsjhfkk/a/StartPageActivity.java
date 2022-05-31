package com.aklsfasad.fsjhfkk.a;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.api.HttpApi;
import com.aklsfasad.fsjhfkk.router.Router;
import com.aklsfasad.fsjhfkk.u.OpenUtil;
import com.aklsfasad.fsjhfkk.u.PreferencesOpenUtil;
import com.aklsfasad.fsjhfkk.u.StatusBarUtil;
import com.aklsfasad.fsjhfkk.w.StartPageRemindDialog;

public class StartPageActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean isSure = false;

    private String phone = "";

    private StartPageRemindDialog startPageRemindDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        StatusBarUtil.setTransparent(this, false);
        isSure = PreferencesOpenUtil.getBool("isSure");
        phone = PreferencesOpenUtil.getString("phone");
        jumpPage();
    }

    private void jumpPage() {
        if (!isSure) {
            startPageRemindDialog = new StartPageRemindDialog(this);
            startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        StartPageActivity.this.finish();
                        return false;
                    }
                    return true;
                }
            });
            startPageRemindDialog.setOnListener(new StartPageRemindDialog.OnListener() {
                @Override
                public void oneBtnClicked() {
                    PreferencesOpenUtil.saveString("uminit", "1");
                    PreferencesOpenUtil.saveBool("isSure", true);
                    OpenUtil.jumpPage(StartPageActivity.this, DlActivity.class);
                    finish();
                }

                @Override
                public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtil.jumpPage(StartPageActivity.this, JumpH5Activity.class, bundle);
                }

                @Override
                public void twoBtnClicked() {
                    finish();
                }

                @Override
                public void ysxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtil.jumpPage(StartPageActivity.this, JumpH5Activity.class, bundle);
                }
            });
            startPageRemindDialog.show();
        } else {
            new Handler().postDelayed(() -> {
                if (TextUtils.isEmpty(phone)) {
                    Router.newIntent(StartPageActivity.this)
                            .to(DlActivity.class)
                            .launch();
                } else {
//                    Router.newIntent(StartPageActivity.this)
//                            .to(HomePageActivity.class)
//                            .launch();
                }
                finish();
            }, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
