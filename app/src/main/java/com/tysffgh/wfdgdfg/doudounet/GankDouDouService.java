package com.tysffgh.wfdgdfg.doudounet;


import com.tysffgh.wfdgdfg.doudoumodel.BaseRespModelDouDou;
import com.tysffgh.wfdgdfg.doudoumodel.CompanDouDouyInfoModel;
import com.tysffgh.wfdgdfg.doudoumodel.DouDouGoodsModel;
import com.tysffgh.wfdgdfg.doudoumodel.LoginRespDouDouModel;
import com.tysffgh.wfdgdfg.doudoumodel.DouDouLoginStatusModel;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankDouDouService {

    @GET("/api/index/is_login")
    Flowable<DouDouLoginStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelDouDou<CompanDouDouyInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelDouDou> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelDouDou<LoginRespDouDouModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelDouDou<LoginRespDouDouModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelDouDou<List<DouDouGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelDouDou<List<DouDouGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelDouDou> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
