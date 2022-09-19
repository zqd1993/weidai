package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet;


import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.BaseRespModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.CompanyJiuJiJieTiaojghsdfInfoModel;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.GoodsModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.LoginJiuJiJieTiaojghsdfStatusModel;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.LoginRespModelJiuJiJieTiaojghsdf;

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

public interface GankJiuJiJieTiaojghsdfService {

    @GET("/api/index/is_login")
    Flowable<LoginJiuJiJieTiaojghsdfStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelJiuJiJieTiaojghsdf> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device
            , @Query("ip") String ip, @Query("userid") String oaid, @Query("useridtype") String useridtype);

    @GET("/app/user/logins")
    Flowable<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelJiuJiJieTiaojghsdf> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
