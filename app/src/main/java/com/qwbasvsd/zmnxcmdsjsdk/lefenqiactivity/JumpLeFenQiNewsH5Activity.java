package com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.TypedValue;
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

import com.qwbasvsd.zmnxcmdsjsdk.R;
import com.qwbasvsd.zmnxcmdsjsdk.mvp.XActivity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.LeFenQiNewsDownloadApkUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.LeFenQiNewsPreferencesOpenUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.StatusBarUtilLeFenQiNews;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

public class JumpLeFenQiNewsH5Activity extends XActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.biaoti_tv)
    TextView biaotiTv;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.h5_view)
    WebView webView;

    private Bundle bundle;
    private String webUrl, biaoti;

    protected static final int RC_PERM = 123;

    private String filePath, apkUrl = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_le_fen_qi_jump_h5;
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilLeFenQiNews.setTransparent(this, false);
        if (LeFenQiNewsPreferencesOpenUtil.getBool("NO_RECORD")) {
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

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int uithdfgj(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float awertdhg(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float utrhfgjh(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int utrhdfh(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float wertsrdty(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float hrthfgj(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public void downFile(String url) {
        ProgressDialog progressDialog = new ProgressDialog(JumpLeFenQiNewsH5Activity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("下载中...");
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);
        String apkName[] = url.split("/");
        LeFenQiNewsDownloadApkUtil.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/", apkName[apkName.length - 1], new LeFenQiNewsDownloadApkUtil.OnDownloadListener() {
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
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int yrthfdh(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float erthgdsh(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float werfg(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
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

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int ietuyhtdjw(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2wertdp(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float nrwtysdfh(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        downFile(apkUrl);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(apkUrl));
        startActivity(intent);
    }

}
