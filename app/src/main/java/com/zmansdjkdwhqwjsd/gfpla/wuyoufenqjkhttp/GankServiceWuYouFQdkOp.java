package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp;


import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.BaseRespWuYouFQdkOpModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.ConfigModelWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.WuYouFQdkOpGoodsModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.LoginRespModelWuYouFQdkOp;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankServiceWuYouFQdkOp {

    @GET("/app/config/getConfig")
    Flowable<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>> getGankData();


    @GET("/app/config/getValue")
    Flowable<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>> getValve(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespWuYouFQdkOpModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/logins")
    Flowable<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespWuYouFQdkOpModel<List<WuYouFQdkOpGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespWuYouFQdkOpModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
