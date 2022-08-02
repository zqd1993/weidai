package com.yunshandaiwert.naeryeasruaert.qufenqiapi;


import com.yunshandaiwert.naeryeasruaert.qufenqim.BannerYunShanDaiModel;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiBaseModel;
import com.yunshandaiwert.naeryeasruaert.qufenqim.ConfigYunShanDaiEntity;
import com.yunshandaiwert.naeryeasruaert.qufenqim.DlModelYunShanDai;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface YunShanDaiInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<YunShanDaiBaseModel<ConfigYunShanDaiEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<YunShanDaiBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<YunShanDaiBaseModel<ConfigYunShanDaiEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<YunShanDaiBaseModel<DlModelYunShanDai>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<YunShanDaiBaseModel<List<YunShanDaiProductModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<YunShanDaiBaseModel<List<BannerYunShanDaiModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<YunShanDaiBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<YunShanDaiBaseModel<DlModelYunShanDai>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
