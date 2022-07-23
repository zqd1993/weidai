package com.wolai.dai.jiekou;


import android.os.Build;
import android.util.Log;

import com.wolai.dai.shiti.JixinBaseModel;
import com.wolai.dai.shiti.JixinConfigEntity;
import com.wolai.dai.shiti.JixinDlModel;
import com.wolai.dai.shiti.JixinProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface JiXinInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<JixinBaseModel<JixinConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<JixinBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<JixinBaseModel<JixinConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<JixinBaseModel<JixinDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    //    public static String getMacAddress() {
//        String result = "";
//        String Mac = "";
//        result = callCmd("busybox ifconfig", "HWaddr");
//
//        if (result == null) {
//            return "网络出错，请检查网络";
//        }
//        if (result.length() > 0 && result.contains("HWaddr")) {
//            Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
//            if (Mac.length() > 1) {
//                result = Mac.toLowerCase();
//            }
//        }
//        return result.trim();
//    }

    @GET("/app/product/productList")
    Flowable<JixinBaseModel<List<JixinProductModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<JixinBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<JixinBaseModel<JixinDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    public static String getMacAddress() {
        String macAddr = "";
//        if (Build.VERSION.SDK_INT <  Build.VERSION_CODES.M){
//            macAddr = MacUtils.getMacAddress0(BaseApplication.getInstance().getApplicationContext());
//        }else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
//            macAddr = MacUtils.getMacAddress(BaseApplication.getInstance().getApplicationContext());
//        }else {
//            macAddr = MacUtils.getMachineHardwareAddress();
//        }
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            Log.d("getMacAddress","6.0及以上、7.0以下");
//            macAddr = MacUtils.getMacAddress(BaseApplication.getInstance().getApplicationContext());
//        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//            Log.d("getMacAddress","7.0及以上");
//            macAddr = MacUtils.getMachineHardwareAddress();
//        }else{
//            Log.d("getMacAddress","6.0以下");
//            macAddr = MacUtils.getMacAddress0(BaseApplication.getInstance().getApplicationContext());
//        }
        Log.d("getMacAddress",macAddr);
        return macAddr;
    }

}
