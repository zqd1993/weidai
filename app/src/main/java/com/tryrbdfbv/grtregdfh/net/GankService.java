package com.tryrbdfbv.grtregdfh.net;


import com.tryrbdfbv.grtregdfh.model.BaseRespModel;
import com.tryrbdfbv.grtregdfh.model.CompanyInfoModel;
import com.tryrbdfbv.grtregdfh.model.GoodsModel;
import com.tryrbdfbv.grtregdfh.model.LoginRespModel;
import com.tryrbdfbv.grtregdfh.model.LoginStatusModel;

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

public interface GankService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModel<CompanyInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModel<LoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModel<LoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModel<List<GoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModel> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
