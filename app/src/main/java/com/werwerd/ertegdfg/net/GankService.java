package com.werwerd.ertegdfg.net;


import com.werwerd.ertegdfg.model.BaseRespYouXinModel;
import com.werwerd.ertegdfg.model.ConfigYouXinModel;
import com.werwerd.ertegdfg.model.GoodsWinAModel;
import com.werwerd.ertegdfg.model.LoginRespYouXinModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespYouXinModel<ConfigYouXinModel>> getGankData();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespYouXinModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespYouXinModel<LoginRespYouXinModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespYouXinModel<LoginRespYouXinModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespYouXinModel<List<GoodsWinAModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespYouXinModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
