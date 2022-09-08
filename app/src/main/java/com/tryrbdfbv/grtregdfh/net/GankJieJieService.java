package com.tryrbdfbv.grtregdfh.net;


import com.tryrbdfbv.grtregdfh.model.BaseRespModelJieJie;
import com.tryrbdfbv.grtregdfh.model.CompanyJieJieInfoModel;
import com.tryrbdfbv.grtregdfh.model.GoodsJieJieModel;
import com.tryrbdfbv.grtregdfh.model.JieJieLoginStatusModel;
import com.tryrbdfbv.grtregdfh.model.LoginRespJieJieModel;

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

public interface GankJieJieService {

    @GET("/api/index/is_login")
    Flowable<JieJieLoginStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelJieJie<CompanyJieJieInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelJieJie> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelJieJie<LoginRespJieJieModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device
            , @Query("ip") String ip, @Query("userid") String oaid, @Query("useridtype") String useridtype);

    @GET("/app/user/logins")
    Flowable<BaseRespModelJieJie<LoginRespJieJieModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelJieJie<List<GoodsJieJieModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelJieJie<List<GoodsJieJieModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelJieJie> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
