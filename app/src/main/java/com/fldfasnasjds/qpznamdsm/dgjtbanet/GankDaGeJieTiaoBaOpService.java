package com.fldfasnasjds.qpznamdsm.dgjtbanet;


import com.fldfasnasjds.qpznamdsm.dgjtbamodel.BaseRespModelDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.ConfigDaGeJieTiaoBaOpModel;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.DaGeJieTiaoBaOpGoodsModel;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.LoginRespDaGeJieTiaoBaOpModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankDaGeJieTiaoBaOpService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>> getValve(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelDaGeJieTiaoBaOp> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelDaGeJieTiaoBaOp<List<DaGeJieTiaoBaOpGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelDaGeJieTiaoBaOp> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
