package com.nfhyrhd.nfhsues.api;


import com.nfhyrhd.nfhsues.m.BaseModelBeiYong;
import com.nfhyrhd.nfhsues.m.ConfigBeiYongEntity;
import com.nfhyrhd.nfhsues.m.BeiYongDlModel;
import com.nfhyrhd.nfhsues.m.ProductModelBeiYong;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceBeiYongUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelBeiYong<ConfigBeiYongEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelBeiYong> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelBeiYong<BeiYongDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModelBeiYong<List<ProductModelBeiYong>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseModelBeiYong> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelBeiYong<BeiYongDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
