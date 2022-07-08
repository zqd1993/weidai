package com.lpsydsnl.jtxqchbb.page;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lpsydsnl.jtxqchbb.R;
import com.lpsydsnl.jtxqchbb.net.HttpMeiJieApi;
import com.lpsydsnl.jtxqchbb.use.OpenMeiJieUtil;
import com.lpsydsnl.jtxqchbb.use.MeiJiePreferencesOpenUtil;
import com.lpsydsnl.jtxqchbb.use.StatusMeiJieBarUtil;
import com.lpsydsnl.jtxqchbb.view.StartPageRemindDialog;
import com.umeng.commonsdk.UMConfigure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MeiJieStartPageActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageRemindDialog startPageRemindDialog;

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean isConnected(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page_meijie);
        StatusMeiJieBarUtil.setTransparent(this, false);
        isSure = MeiJiePreferencesOpenUtil.getBool("isSure");
        phone = MeiJiePreferencesOpenUtil.getString("phone");
        sendRequestWithOkHttp();
    }

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

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean aeragfg(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean bndghfgh(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    private void showDialog() {
        Looper.prepare();
        startPageRemindDialog = new StartPageRemindDialog(this);
        startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    MeiJieStartPageActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageRemindDialog.setOnListener(new StartPageRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
                initUm();
                MeiJiePreferencesOpenUtil.saveBool("isSure", true);
                OpenMeiJieUtil.jumpPage(MeiJieStartPageActivity.this, DlMeiJieActivity.class);
                finish();
            }

            @Override
            public void zcxyClicked() {
                if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putString("url", MeiJiePreferencesOpenUtil.getString("AGREEMENT") + HttpMeiJieApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenMeiJieUtil.jumpPage(MeiJieStartPageActivity.this, MeiJieJumpH5Activity.class, bundle);
                }
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putString("url", MeiJiePreferencesOpenUtil.getString("AGREEMENT") + HttpMeiJieApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenMeiJieUtil.jumpPage(MeiJieStartPageActivity.this, MeiJieJumpH5Activity.class, bundle);
                }
            }
        });
        startPageRemindDialog.show();
        Looper.loop();
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean rtergsfdg(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean csdEr(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7750.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                MeiJiePreferencesOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                MeiJiePreferencesOpenUtil.saveString("AGREEMENT", net[1]);
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
            initUm();
            if (TextUtils.isEmpty(phone)) {
                OpenMeiJieUtil.jumpPage(MeiJieStartPageActivity.this, DlMeiJieActivity.class);
            } else {
                OpenMeiJieUtil.jumpPage(MeiJieStartPageActivity.this, MainActivityMeiJie.class);
            }
            finish();
        } else {
            showDialog();
        }
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean yuyjthj(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean bdfgrdtd(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUm() {
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (!UMConfigure.isInit) {
            UMConfigure.setLogEnabled(true);
            Log.d("youmeng", "zhuche chenggong");
            //友盟正式初始化
//            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
            // 参数一：当前上下文context；
            // 参数二：应用申请的Appkey（需替换）；
            // 参数三：渠道名称；
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
            UMConfigure.init(this, "62c7260905844627b5dd9f8a", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean wedsdfs(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean myjhyjg(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
