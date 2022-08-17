package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet;


import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.BaseRespModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.CompanyXianjinChaoShiInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.GoodsModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.LoginXianjinChaoShiRespModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.LoginXianjinChaoShiStatusModel;

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

public interface GankXianjinChaoShiService {

    @GET("/api/index/is_login")
    Flowable<LoginXianjinChaoShiStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelXianjinChaoShi> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelXianjinChaoShi> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
