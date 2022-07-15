package com.retdfhxfghj.bdrhrtuyfgj.xinyongnet;


import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongBaseRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.CompanyInfoModelXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongGoodsModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongLoginRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.LoginStatusModelXinYong;

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

public interface XinYongGankService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModelXinYong> getGankData();

    @GET("/api/index/gs")
    Flowable<XinYongBaseRespModel<CompanyInfoModelXinYong>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<XinYongBaseRespModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<XinYongBaseRespModel<XinYongLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<XinYongBaseRespModel<XinYongLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<XinYongBaseRespModel<List<XinYongGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<XinYongBaseRespModel<List<XinYongGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<XinYongBaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
