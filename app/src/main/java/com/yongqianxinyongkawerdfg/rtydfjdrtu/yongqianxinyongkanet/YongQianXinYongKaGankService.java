package com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet;


import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaBaseRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.CompanyInfoModelYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaGoodsModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaLoginRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.LoginStatusModelYongQianXinYongKa;

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

public interface YongQianXinYongKaGankService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModelYongQianXinYongKa> getGankData();

    @GET("/api/index/gs")
    Flowable<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<YongQianXinYongKaBaseRespModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<YongQianXinYongKaBaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
