package com.chenqi.lecheng.shadnaihttp;


import com.chenqi.lecheng.shadnaimodel.BaseRespHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.ConfigHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.GoodsWinAHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.LoginRespHaoJieModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankHaoJieService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespHaoJieModel<ConfigHaoJieModel>> getConfig();

    @GET("/app/user/logins")
    Flowable<BaseRespHaoJieModel<LoginRespHaoJieModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespHaoJieModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Flowable<BaseRespHaoJieModel<List<GoodsWinAHaoJieModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespHaoJieModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespHaoJieModel<LoginRespHaoJieModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

}
