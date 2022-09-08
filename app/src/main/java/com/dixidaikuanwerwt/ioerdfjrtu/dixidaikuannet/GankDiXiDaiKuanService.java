package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet;


import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.BaseRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.CompanyDiXiDaiKuanInfoModel;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.GoodsModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.LoginRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.LoginDiXiDaiKuanStatusModel;

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

public interface GankDiXiDaiKuanService {

    @GET("/api/index/is_login")
    Flowable<LoginDiXiDaiKuanStatusModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelDiXiDaiKuan> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device
            , @Query("ip") String ip, @Query("userid") String userid, @Query("useridtype") String useridtype);

    @GET("/app/user/logins")
    Flowable<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelDiXiDaiKuan> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
