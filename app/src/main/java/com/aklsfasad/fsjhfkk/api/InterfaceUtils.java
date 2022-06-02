package com.aklsfasad.fsjhfkk.api;


import com.aklsfasad.fsjhfkk.m.BaseModel;
import com.aklsfasad.fsjhfkk.m.ConfigEntity;
import com.aklsfasad.fsjhfkk.m.DlModel;
import com.aklsfasad.fsjhfkk.m.ProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModel<ConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseModel<DlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModel<List<ProductModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModel<DlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
