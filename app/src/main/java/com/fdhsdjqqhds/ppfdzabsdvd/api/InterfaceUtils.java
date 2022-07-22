package com.fdhsdjqqhds.ppfdzabsdvd.api;


import com.fdhsdjqqhds.ppfdzabsdvd.model.BannerModel;
import com.fdhsdjqqhds.ppfdzabsdvd.model.BaseModel;
import com.fdhsdjqqhds.ppfdzabsdvd.model.ConfigEntity;
import com.fdhsdjqqhds.ppfdzabsdvd.model.DlModel;
import com.fdhsdjqqhds.ppfdzabsdvd.model.ProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModel<ConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModel<ConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModel<DlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModel<List<ProductModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<BaseModel<List<BannerModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<BaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModel<DlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
