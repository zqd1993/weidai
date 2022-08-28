package com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss;


import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.BaseRespQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.CompanyInfoModelQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.GoodsQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.LoginStatusModelQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.LoginQueQianMaBossRespModel;

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

public interface GankQueQianMaBossService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModelQueQianMaBoss> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespQueQianMaBossModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespQueQianMaBossModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
