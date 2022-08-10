package com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet;


import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.CompanyInfoModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.FenQiHuanQianBeiGoodsModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.LoginRespModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.LoginStatusFenQiHuanQianBeiModel;

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

public interface FenQiHuanQianBeiGankService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusFenQiHuanQianBeiModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespFenQiHuanQianBeiModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespFenQiHuanQianBeiModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
