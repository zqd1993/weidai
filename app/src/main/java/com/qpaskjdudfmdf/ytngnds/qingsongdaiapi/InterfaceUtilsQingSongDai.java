package com.qpaskjdudfmdf.ytngnds.qingsongdaiapi;


import com.qpaskjdudfmdf.ytngnds.qingsongdaim.BaseQingSongDaiModel;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.ConfigEntityQingSongDai;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.DlQingSongDaiModel;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.ProductQingSongDaiModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsQingSongDai {

    @GET("/app/config/getConfig")
    Flowable<BaseQingSongDaiModel<ConfigEntityQingSongDai>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseQingSongDaiModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseQingSongDaiModel<ConfigEntityQingSongDai>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseQingSongDaiModel<DlQingSongDaiModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseQingSongDaiModel<List<ProductQingSongDaiModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseQingSongDaiModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseQingSongDaiModel<DlQingSongDaiModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
