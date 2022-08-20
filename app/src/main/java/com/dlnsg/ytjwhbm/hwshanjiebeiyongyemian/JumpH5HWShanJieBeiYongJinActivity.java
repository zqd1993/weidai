package com.dlnsg.ytjwhbm.hwshanjiebeiyongyemian;

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

import com.dlnsg.ytjwhbm.R;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongjiekou.HWShanJieBeiYongJinApi;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongjingongju.PreferenceHWShanJieBeiYongJinOpenUtil;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.BaseHWShanJieBeiYongJinModel;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.ConfigHWShanJieBeiYongJinEntity;
import com.dlnsg.ytjwhbm.mvp.XActivity;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongjingongju.DownloadApkUtilHWShanJieBeiYongJin;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongjingongju.StatusBarHWShanJieBeiYongJinUtil;
import com.dlnsg.ytjwhbm.net.ApiSubscriber;
import com.dlnsg.ytjwhbm.net.NetError;
import com.dlnsg.ytjwhbm.net.XApi;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

public class JumpH5HWShanJieBeiYongJinActivity extends XActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.biaoti_tv)
    TextView biaotiTv;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.h5_view)
    WebView webView;

    private static final String[] SYSTEM_BRANDS = {
            "huawei",
            "honor",
            "xiaomi",
            "oneplus",
            "oppo",
            "vivo",
            "meizu",
            "samsung",
            "lenovo",
            "zte",
            "lg",
            "sony",
            "coolpad"
    };

    private Bundle bundle;
    private String webUrl, biaoti;

    protected static final int RC_PERM = 123;

    private String filePath, apkUrl = "";

    @Override
    public int getLayoutId() {
        return R.layout.hw_shan_jie_bei_yong_jie_activity_jump_h5;
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
        StatusBarHWShanJieBeiYongJinUtil.setTransparent(this, false);
        if (PreferenceHWShanJieBeiYongJinOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        bundle = getIntent().getExtras();
        if (bundle.containsKey("biaoti"))
            biaoti = bundle.getString("biaoti");
        if (bundle.containsKey("url"))
            webUrl = bundle.getString("url");
        biaotiTv.setText(biaoti);
        backImage.setOnClickListener(v -> {
            finish();
        });
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setTextZoom(100);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            apkUrl = url;
            checkPermission();
        });
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
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
        return android.os.Build.BRAND;
    }

    /**
     *
     * @param brand 需转为小写
     * @return true or false
     */
    public static boolean isContainBrand(String brand){
        for (String systemBrand : SYSTEM_BRANDS) {
            if (systemBrand.equals(brand)){
                return true;
            }
        }
        return false;
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

    public void downFile(String url) {
        ProgressDialog progressDialog = new ProgressDialog(JumpH5HWShanJieBeiYongJinActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("下载中...");
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);
        String apkName[] = url.split("/");
        DownloadApkUtilHWShanJieBeiYongJin.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/", apkName[apkName.length - 1], new DownloadApkUtilHWShanJieBeiYongJin.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                filePath = file.getPath();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    // Android8.0之前，直接安装Apk
                    installApk();
                    return;
                }
                boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {
                    // 权限没有打开则提示用户去手动打开
                    Uri packageURI = Uri.parse("package:" + getPackageName());
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    startActivityForResult(intent, 1001);
                } else {
                    installApk();
                }

            }

            @Override
            public void onDownloading(int progress) {
                progressDialog.setProgress(progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                //下载异常进行相关提示操作

            }
        });
    }

    /**
     * 未知来源安装权限申请回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1001 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 未知来源安装应用权限开启
            boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
                installApk();
            }
        }
    }

    /**
     * 安装最新Apk
     */
    private void installApk() {
        File file = new File(filePath);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String packageName = context.getApplicationContext().getPackageName();
                String authority = new StringBuilder(packageName).append(".provider").toString();
                Uri uri = FileProvider.getUriForFile(context, authority, file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            } else {
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(apkUrl));
        startActivity(intent);
    }

    private void checkPermission() {
        String[] per = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, per)) {
            downFile(apkUrl);
        } else {
            EasyPermissions.requestPermissions(this, "需要允许读写内存卡权限",
                    RC_PERM, per);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        downFile(apkUrl);
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
        HWShanJieBeiYongJinApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferenceHWShanJieBeiYongJinOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferenceHWShanJieBeiYongJinOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
