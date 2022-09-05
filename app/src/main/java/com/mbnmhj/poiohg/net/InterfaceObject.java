package com.mbnmhj.poiohg.net;


import com.mbnmhj.poiohg.entity.MainModel;
import com.mbnmhj.poiohg.entity.CFEntity;
import com.mbnmhj.poiohg.entity.DengLuModel;
import com.mbnmhj.poiohg.entity.MoreModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceObject {

    @GET("/app/config/getConfig")
    Flowable<MainModel<CFEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<MainModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<MainModel<CFEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<MainModel<DengLuModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                           @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<MainModel<List<MoreModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<MainModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<MainModel<DengLuModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
