package com.rihdkauecgh.plihgnytrvfws.http;


import com.rihdkauecgh.plihgnytrvfws.models.BaseRespXiaoNiuModel;
import com.rihdkauecgh.plihgnytrvfws.models.ConfigModelXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.models.XiaoNiuGoodsModel;
import com.rihdkauecgh.plihgnytrvfws.models.LoginRespModelXiaoNiu;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankServiceXiaoNiu {

    @GET("/app/config/getConfig")
    Flowable<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>> getGankData();


    @GET("/app/config/getValue")
    Flowable<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>> getValve(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespXiaoNiuModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespXiaoNiuModel<LoginRespModelXiaoNiu>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespXiaoNiuModel<LoginRespModelXiaoNiu>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespXiaoNiuModel<List<XiaoNiuGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespXiaoNiuModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
