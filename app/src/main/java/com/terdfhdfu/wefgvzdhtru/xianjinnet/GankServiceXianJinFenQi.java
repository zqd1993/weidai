package com.terdfhdfu.wefgvzdhtru.xianjinnet;


import com.terdfhdfu.wefgvzdhtru.xianjinmodel.BaseRespModelXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.XianJinFenQiCompanyInfoModel;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.GoodsModelXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.XianJinFenQiLoginRespModel;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.LoginStatusModelXianJinFenQi;

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

public interface GankServiceXianJinFenQi {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModelXianJinFenQi> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelXianJinFenQi> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelXianJinFenQi<XianJinFenQiLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelXianJinFenQi<XianJinFenQiLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelXianJinFenQi> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
