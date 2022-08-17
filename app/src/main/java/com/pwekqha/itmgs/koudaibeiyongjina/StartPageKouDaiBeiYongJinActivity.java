package com.pwekqha.itmgs.koudaibeiyongjina;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.pwekqha.itmgs.R;
import com.pwekqha.itmgs.koudaibeiyongjinapi.HttpKouDaiBeiYongJinApi;
import com.pwekqha.itmgs.koudaibeiyongjinw.StartPageRemindDialogKouDaiBeiYongJin;
import com.pwekqha.itmgs.mvp.XActivity;
import com.pwekqha.itmgs.koudaibeiyongjinu.OpenUtilKouDaiBeiYongJin;
import com.pwekqha.itmgs.koudaibeiyongjinu.KouDaiBeiYongJinPreferencesOpenUtil;
import com.pwekqha.itmgs.koudaibeiyongjinu.StatusBarKouDaiBeiYongJinUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartPageKouDaiBeiYongJinActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageRemindDialogKouDaiBeiYongJin startPageRemindDialogKouDaiBeiYongJin;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
            }
        }, 500);
    }

    private void showDialog() {
//        Looper.prepare();
        startPageRemindDialogKouDaiBeiYongJin = new StartPageRemindDialogKouDaiBeiYongJin(this);
        startPageRemindDialogKouDaiBeiYongJin.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    StartPageKouDaiBeiYongJinActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageRemindDialogKouDaiBeiYongJin.setOnListener(new StartPageRemindDialogKouDaiBeiYongJin.OnListener() {
            @Override
            public void oneBtnClicked() {
                KouDaiBeiYongJinPreferencesOpenUtil.saveBool("isSure", true);
                startPageRemindDialogKouDaiBeiYongJin.dismiss();
                OpenUtilKouDaiBeiYongJin.jumpPage(StartPageKouDaiBeiYongJinActivity.this, DlKouDaiBeiYongJinActivity.class);
                finish();
            }

            @Override
            public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpKouDaiBeiYongJinApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtilKouDaiBeiYongJin.jumpPage(StartPageKouDaiBeiYongJinActivity.this, JumpH5KouDaiBeiYongJinActivity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpKouDaiBeiYongJinApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtilKouDaiBeiYongJin.jumpPage(StartPageKouDaiBeiYongJinActivity.this, JumpH5KouDaiBeiYongJinActivity.class, bundle);
            }
        });
        startPageRemindDialogKouDaiBeiYongJin.show();
//        Looper.loop();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7731.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                KouDaiBeiYongJinPreferencesOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                KouDaiBeiYongJinPreferencesOpenUtil.saveString("AGREEMENT", net[1]);
                                Thread.sleep(1000);
                                jumpPage();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jumpPage() {
        if (isSure) {
            if (TextUtils.isEmpty(phone)) {
                OpenUtilKouDaiBeiYongJin.jumpPage(StartPageKouDaiBeiYongJinActivity.this, DlKouDaiBeiYongJinActivity.class);
            } else {
                OpenUtilKouDaiBeiYongJin.jumpPage(StartPageKouDaiBeiYongJinActivity.this, MainActivityKouDaiBeiYongJin.class);
            }
            finish();
        } else {
            showDialog();
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

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKouDaiBeiYongJinUtil.setTransparent(this, false);
        isSure = KouDaiBeiYongJinPreferencesOpenUtil.getBool("isSure");
        phone = KouDaiBeiYongJinPreferencesOpenUtil.getString("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_page_kou_dai_bei_yong_jin;
    }

    @Override
    public Object newP() {
        return null;
    }
}
