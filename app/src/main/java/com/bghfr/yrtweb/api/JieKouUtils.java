package com.bghfr.yrtweb.api;


import com.bghfr.yrtweb.m.MainModel;
import com.bghfr.yrtweb.m.SetEntity;
import com.bghfr.yrtweb.m.DengluModel;
import com.bghfr.yrtweb.m.ShangPinModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface JieKouUtils {

    @GET("/app/config/getConfig")
    Flowable<MainModel<SetEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<MainModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<MainModel<DengluModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<MainModel<List<ShangPinModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<MainModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<MainModel<DengluModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
