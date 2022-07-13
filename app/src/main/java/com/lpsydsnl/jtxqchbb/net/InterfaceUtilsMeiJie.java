package com.lpsydsnl.jtxqchbb.net;


import com.lpsydsnl.jtxqchbb.model.MeiJieBaseModel;
import com.lpsydsnl.jtxqchbb.model.ConfigMeiJieEntity;
import com.lpsydsnl.jtxqchbb.model.DlModelMeiJie;
import com.lpsydsnl.jtxqchbb.model.ProductMeiJieModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsMeiJie {

    @GET("/app/config/getConfig")
    Flowable<MeiJieBaseModel<ConfigMeiJieEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<MeiJieBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<MeiJieBaseModel<ConfigMeiJieEntity>> getValve(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<MeiJieBaseModel<DlModelMeiJie>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<MeiJieBaseModel<List<ProductMeiJieModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<MeiJieBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<MeiJieBaseModel<DlModelMeiJie>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
