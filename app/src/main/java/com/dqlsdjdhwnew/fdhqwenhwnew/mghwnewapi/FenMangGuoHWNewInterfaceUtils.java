package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewapi;


import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewBaseModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewConfigEntity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.DlMangGuoHWNewModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.ProductModelMangGuoHWNew;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface FenMangGuoHWNewInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<MangGuoHWNewBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<MangGuoHWNewBaseModel<DlMangGuoHWNewModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<MangGuoHWNewBaseModel<List<ProductModelMangGuoHWNew>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<MangGuoHWNewBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<MangGuoHWNewBaseModel<DlMangGuoHWNewModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
