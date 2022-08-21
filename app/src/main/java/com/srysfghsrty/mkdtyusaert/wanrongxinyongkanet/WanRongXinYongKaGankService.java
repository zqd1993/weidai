package com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet;


import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.BaseRespModelWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.CompanyInfoWanRongXinYongKaModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.LoginStatusWanRongXinYongKaModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.WanRongXinYongKaGoodsModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.LoginRespModelWanRongXinYongKa;

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

public interface WanRongXinYongKaGankService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusWanRongXinYongKaModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelWanRongXinYongKa> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelWanRongXinYongKa<List<WanRongXinYongKaGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelWanRongXinYongKa<List<WanRongXinYongKaGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelWanRongXinYongKa> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
