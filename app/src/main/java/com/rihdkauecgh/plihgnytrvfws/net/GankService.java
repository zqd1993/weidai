package com.rihdkauecgh.plihgnytrvfws.net;


import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.model.GoodsModel;
import com.rihdkauecgh.plihgnytrvfws.model.LoginRespModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModel<ConfigModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModel<ConfigModel>> getValue(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModel<LoginRespModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModel<LoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModel<List<GoodsModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
