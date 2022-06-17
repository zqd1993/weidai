package com.bghfr.yrtweb.net;


import com.bghfr.yrtweb.model.BaseModel;
import com.bghfr.yrtweb.model.PeiZhiModel;
import com.bghfr.yrtweb.model.ProductModel;
import com.bghfr.yrtweb.model.LoginModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {

    @GET("/app/config/getConfig")
    Flowable<BaseModel<PeiZhiModel>> getGankData();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseModel<LoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseModel<LoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModel<List<ProductModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
