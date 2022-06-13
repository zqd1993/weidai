package com.rihdkauecgh.plihgnytrvfws.net;


import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.CompanyInfoModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.model.GoodsModel;
import com.rihdkauecgh.plihgnytrvfws.model.LoginRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.LoginStatusModel;
import com.rihdkauecgh.plihgnytrvfws.model.RequModel;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Flowable<BaseRespModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/api/index/logincode")
    Flowable<BaseRespModel<LoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModel<LoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModel<List<GoodsModel>>> productList(@Body RequestBody model);

    @GET("/api/index/aindex")
    Flowable<BaseRespModel> aindex(@Header("token") String token);

    @GET("/app/product/productClick")
    Flowable<BaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
