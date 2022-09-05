package com.aklsfasad.fsjhfkk.net;


import com.aklsfasad.fsjhfkk.model.BaseRespHuiMinModel;
import com.aklsfasad.fsjhfkk.model.ConfigHuiMinModel;
import com.aklsfasad.fsjhfkk.model.GoodsHuiMinModel;
import com.aklsfasad.fsjhfkk.model.LoginRespHuiMinModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespHuiMinModel<ConfigHuiMinModel>> getGankData();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespHuiMinModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseRespHuiMinModel<ConfigHuiMinModel>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespHuiMinModel<LoginRespHuiMinModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/logins")
    Flowable<BaseRespHuiMinModel<LoginRespHuiMinModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespHuiMinModel<List<GoodsHuiMinModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespHuiMinModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
