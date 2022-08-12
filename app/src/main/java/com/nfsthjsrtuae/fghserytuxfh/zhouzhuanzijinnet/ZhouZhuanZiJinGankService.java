package com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet;


import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinCompanyInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinGoodsModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginRespModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginStatusModel;

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

public interface ZhouZhuanZiJinGankService {

    @GET("/api/index/is_login")
    Flowable<ZhouZhuanZiJinLoginStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<ZhouZhuanZiJinBaseRespModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<ZhouZhuanZiJinBaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
