package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi;


import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DaiKuanMiaoXiaBaseModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ConfigEntityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DlDaiKuanMiaoXiaModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ProductDaiKuanMiaoXiaModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsDaiKuanMiaoXia {

    @GET("/app/config/getConfig")
    Flowable<DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<DaiKuanMiaoXiaBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<DaiKuanMiaoXiaBaseModel<DlDaiKuanMiaoXiaModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<DaiKuanMiaoXiaBaseModel<List<ProductDaiKuanMiaoXiaModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<DaiKuanMiaoXiaBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<DaiKuanMiaoXiaBaseModel<DlDaiKuanMiaoXiaModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
