package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet;


import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.GoodsModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqBaseRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.CompanyInfoModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.LoginStatusModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqLoginRespModel;

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

public interface GankYjjdFqjqService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModelYjjdFqjq> getGankData();

    @GET("/api/index/gs")
    Flowable<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<YjjdFqjqBaseRespModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device
            , @Query("ip") String ip, @Query("userid") String oaid, @Query("useridtype") String useridtype);

    @GET("/app/user/logins")
    Flowable<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<YjjdFqjqBaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
