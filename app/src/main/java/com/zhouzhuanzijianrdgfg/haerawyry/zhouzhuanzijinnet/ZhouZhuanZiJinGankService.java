package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet;


import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinCompanyInfoModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinGoodsModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginRespModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginStatusModel;

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
