package com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr;


import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.BaseRespModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.CompanyInfoModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfGoodsModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginRespModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginStatusModel;

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

public interface GankServiceRuYiJieKuanAdgFsdf {

    @GET("/api/index/is_login")
    Flowable<RuYiJieKuanAdgFsdfLoginStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelRuYiJieKuanAdgFsdf> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device
            , @Query("ip") String ip, @Query("userid") String oaid, @Query("useridtype") String useridtype);

    @GET("/app/user/logins")
    Flowable<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelRuYiJieKuanAdgFsdf> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
