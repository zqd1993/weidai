package com.tergbaedd.bbbdstrga.sjdnet;


import com.tergbaedd.bbbdstrga.sldmodel.BaseRespModelShouJiDai;
import com.tergbaedd.bbbdstrga.sldmodel.ConfigShouJiDaiModel;
import com.tergbaedd.bbbdstrga.sldmodel.ShouJiDaiGoodsModel;
import com.tergbaedd.bbbdstrga.sldmodel.LoginRespShouJiDaiModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankShouJiDaiService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelShouJiDai<ConfigShouJiDaiModel>> getGankData();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelShouJiDai> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseRespModelShouJiDai<ConfigShouJiDaiModel>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelShouJiDai<List<ShouJiDaiGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelShouJiDai> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
