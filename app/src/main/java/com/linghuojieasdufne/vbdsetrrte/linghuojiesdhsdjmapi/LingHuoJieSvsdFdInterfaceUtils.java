package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi;


import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.BaseModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ConfigEntityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.DlModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ProductModelLingHuoJieSvsdFd;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface LingHuoJieSvsdFdInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelLingHuoJieSvsdFd> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelLingHuoJieSvsdFd<DlModelLingHuoJieSvsdFd>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<BaseModelLingHuoJieSvsdFd<List<ProductModelLingHuoJieSvsdFd>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseModelLingHuoJieSvsdFd> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelLingHuoJieSvsdFd<DlModelLingHuoJieSvsdFd>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
