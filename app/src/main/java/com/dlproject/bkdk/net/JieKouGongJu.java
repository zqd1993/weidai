package com.dlproject.bkdk.net;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.dlproject.bkdk.bean.ParentModel;
import com.dlproject.bkdk.bean.PeiZhiEntity;
import com.dlproject.bkdk.bean.DengGeLuModel;
import com.dlproject.bkdk.bean.ChanPinModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface JieKouGongJu {

    @GET("/app/config/getConfig")
    Flowable<ParentModel<PeiZhiEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<ParentModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<ParentModel<PeiZhiEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<ParentModel<DengGeLuModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<ParentModel<List<ChanPinModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<ParentModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<ParentModel<DengGeLuModel>> logins(@Query("phone") String phone, @Query("ip") String ip);


    /**
     * 历史地址
     */
    public final static String HISTORYADDRESS = "HISTORYADDRESS";
    /**
     * 扫描类型
     */
    public final static String QRCODETYPE = "QRCODETYPE";

    public final static String BARCODE = "BARCODE";

    //    获取默认文件存储目录
    public static String getUpdateFileDir() {
        return "amez/apk/";
    }

    public final static String INTENT_KEY = "update_dialog_values";

    // 360手机助手
    public static final String MARKET = "com.qihoo.appstore";
    // 淘宝手机助手
    public static final String MARKET_TAOBAO = "com.taobao.appcenter";
    // 应用宝
    public static final String MARKET_QQDOWNLOADER = "com.tencent.Android.qqdownloader";
    // 安卓市场
    public static final String MARKET_HIAPK = "com.hiapk.marketpho";
    // 安智市场
    public static final String MARKET_GOAPK = "cn.goapk.market";
    // 包名
    public static final String APP_PACKAGE_NAME = "com.*.*";


    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 启动App
     *
     * @param context
     */
    public static void launchapp(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }


}
