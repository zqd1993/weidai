package com.rongyidai.eetzgfzdfh.net;


import com.rongyidai.eetzgfzdfh.model.BaseRespModelRongYiDai;
import com.rongyidai.eetzgfzdfh.model.RongYiDaiCompanyInfoModel;
import com.rongyidai.eetzgfzdfh.model.RongYiDaiGoodsModel;
import com.rongyidai.eetzgfzdfh.model.RongYiDaiLoginRespModel;
import com.rongyidai.eetzgfzdfh.model.LoginRongYiDaiStatusModel;

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

public interface RongYiDaiGankService {

    @GET("/api/index/is_login")
    Flowable<LoginRongYiDaiStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelRongYiDai> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelRongYiDai<RongYiDaiLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelRongYiDai<RongYiDaiLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelRongYiDai<List<RongYiDaiGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelRongYiDai<List<RongYiDaiGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelRongYiDai> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
