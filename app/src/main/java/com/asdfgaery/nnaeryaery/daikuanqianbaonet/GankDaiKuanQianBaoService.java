package com.asdfgaery.nnaeryaery.daikuanqianbaonet;


import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.BaseRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.CompanyDaiKuanQianBaoInfoModel;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.GoodsModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.LoginRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.LoginDaiKuanQianBaoStatusModel;

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

public interface GankDaiKuanQianBaoService {

    @GET("/api/index/is_login")
    Flowable<LoginDaiKuanQianBaoStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelDaiKuanQianBao> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelDaiKuanQianBao> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
