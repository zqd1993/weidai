package com.jwcf.zdclm.net;


import com.jwcf.zdclm.mod.YingJiBaseRespModel;
import com.jwcf.zdclm.mod.ConfigYingJiModel;
import com.jwcf.zdclm.mod.GoodsModelYingJi;
import com.jwcf.zdclm.mod.YingJiLoginRespModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface YingJiGankService {

    @GET("/app/config/getConfig")
    Flowable<YingJiBaseRespModel<ConfigYingJiModel>> getGankData();

    @GET("/app/user/sendVerifyCode")
    Flowable<YingJiBaseRespModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<YingJiBaseRespModel<YingJiLoginRespModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<YingJiBaseRespModel<YingJiLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<YingJiBaseRespModel<List<GoodsModelYingJi>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<YingJiBaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
