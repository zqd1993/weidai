package com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkanet;


import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.BaseRespModelKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.CompanyKouDaiInfoModel;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.GoodsModelKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.LoginRespModelKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.LoginKouDaiStatusModel;

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

public interface GankServiceKouDai {

    @GET("/api/index/is_login")
    Flowable<LoginKouDaiStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelKouDai<CompanyKouDaiInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelKouDai> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelKouDai<LoginRespModelKouDai>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelKouDai<LoginRespModelKouDai>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelKouDai<List<GoodsModelKouDai>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelKouDai<List<GoodsModelKouDai>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelKouDai> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
