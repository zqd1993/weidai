package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet;


import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.BaseRespModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.CompanyKuaiDianFenQiDaiInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.GoodsModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.LoginKuaiDianFenQiDaiRespModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.LoginKuaiDianFenQiDaiStatusModel;

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

public interface GankKuaiDianFenQiDaiService {

    @GET("/api/index/is_login")
    Flowable<LoginKuaiDianFenQiDaiStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelKuaiDianFenQiDai> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelKuaiDianFenQiDai> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
