package com.xvhyrt.ghjtyu.net;


import com.xvhyrt.ghjtyu.bean.ParentModel;
import com.xvhyrt.ghjtyu.bean.PeiZhiEntity;
import com.xvhyrt.ghjtyu.bean.DengGeLuModel;
import com.xvhyrt.ghjtyu.bean.ChanPinModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface JieKouGongJu {

    @GET("/app/config/getConfig")
    Flowable<ParentModel<PeiZhiEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<ParentModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<ParentModel<DengGeLuModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<ParentModel<List<ChanPinModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<ParentModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<ParentModel<DengGeLuModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
