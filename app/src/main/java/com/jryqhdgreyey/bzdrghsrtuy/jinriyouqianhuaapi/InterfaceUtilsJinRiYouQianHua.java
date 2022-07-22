package com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaapi;


import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaBannerModel;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaBaseModel;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaConfigEntity;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.DlModelJinRiYouQianHua;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsJinRiYouQianHua {

    @GET("/app/config/getConfig")
    Flowable<JinRiYouQianHuaBaseModel<JinRiYouQianHuaConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<JinRiYouQianHuaBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<JinRiYouQianHuaBaseModel<JinRiYouQianHuaConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<JinRiYouQianHuaBaseModel<DlModelJinRiYouQianHua>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaProductModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaBannerModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<JinRiYouQianHuaBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<JinRiYouQianHuaBaseModel<DlModelJinRiYouQianHua>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
