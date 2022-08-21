package com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai;


import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiBaseRespModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiCompanyInfoModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiGoodsModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiLoginRespModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiLoginStatusModel;

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

public interface JiuJiJieDaiGankService {

    @GET("/api/index/is_login")
    Flowable<JiuJiJieDaiLoginStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<JiuJiJieDaiBaseRespModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<JiuJiJieDaiBaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
