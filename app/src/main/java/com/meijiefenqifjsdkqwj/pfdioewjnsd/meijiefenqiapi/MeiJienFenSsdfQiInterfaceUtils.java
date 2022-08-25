package com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiapi;


import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.BaseMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.ConfigEntityMeiJienFenSsdfQi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.DlMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.ProductMeiJienFenSsdfQiModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface MeiJienFenSsdfQiInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseMeiJienFenSsdfQiModel<ConfigEntityMeiJienFenSsdfQi>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseMeiJienFenSsdfQiModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseMeiJienFenSsdfQiModel<ConfigEntityMeiJienFenSsdfQi>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseMeiJienFenSsdfQiModel<DlMeiJienFenSsdfQiModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseMeiJienFenSsdfQiModel<List<ProductMeiJienFenSsdfQiModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseMeiJienFenSsdfQiModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseMeiJienFenSsdfQiModel<DlMeiJienFenSsdfQiModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
