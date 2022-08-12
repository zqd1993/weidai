package com.jieuacnisdoert.naweodfigety.jiekuanzhijianet;


import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.BaseRespModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.CompanyInfoModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaGoodsModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaLoginRespModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaLoginStatusModel;

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

public interface GankServiceJieKuanZhiJia {

    @GET("/api/index/is_login")
    Flowable<JieKuanZhiJiaLoginStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelJieKuanZhiJia> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelJieKuanZhiJia> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
