package com.jinyu.xiaopu.fenfujienet;


import com.jinyu.xiaopu.fenfujiemodel.BaseRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.FenFuJieConfigModel;
import com.jinyu.xiaopu.fenfujiemodel.GoodsFenFuJieModel;
import com.jinyu.xiaopu.fenfujiemodel.LoginRespModelFenFuJie;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankServiceFenFuJie {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelFenFuJie<FenFuJieConfigModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModelFenFuJie<FenFuJieConfigModel>> getValue(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelFenFuJie> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelFenFuJie<LoginRespModelFenFuJie>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/logins")
    Flowable<BaseRespModelFenFuJie<LoginRespModelFenFuJie>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelFenFuJie<List<GoodsFenFuJieModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelFenFuJie> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
