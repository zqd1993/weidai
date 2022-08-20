package com.dlnsg.ytjwhbm.hwshanjiebeiyongjiekou;


import android.util.Log;

import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.BaseHWShanJieBeiYongJinModel;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.ConfigHWShanJieBeiYongJinEntity;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.DlModelHWShanJieBeiYongJin;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.ProductHWShanJieBeiYongJinModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface HWShanJieBeiYongJinInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseHWShanJieBeiYongJinModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseHWShanJieBeiYongJinModel<DlModelHWShanJieBeiYongJin>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

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
    Flowable<BaseHWShanJieBeiYongJinModel<List<ProductHWShanJieBeiYongJinModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseHWShanJieBeiYongJinModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseHWShanJieBeiYongJinModel<DlModelHWShanJieBeiYongJin>> logins(@Query("phone") String phone, @Query("ip") String ip);

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
