package com.fghyugfg.mjkyhgb.tsndkapi;


import com.fghyugfg.mjkyhgb.tsndkm.BaseTSNDKModel;
import com.fghyugfg.mjkyhgb.tsndkm.ConfigTSNDKEntity;
import com.fghyugfg.mjkyhgb.tsndkm.TSNDKDlModel;
import com.fghyugfg.mjkyhgb.tsndkm.ProductModelTSNDK;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface TSNDKInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseTSNDKModel<ConfigTSNDKEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseTSNDKModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseTSNDKModel<ConfigTSNDKEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseTSNDKModel<TSNDKDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseTSNDKModel<List<ProductModelTSNDK>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseTSNDKModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseTSNDKModel<TSNDKDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
