package com.rihdkauecgh.plihgnytrvfws.netweifenqi;


import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.BaseRespModelWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.ConfigWeiFenQiModel;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.WeiFenQiGoodsModel;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.WeiFenQiLoginRespModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface WeiFenQiGankService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>> getValue(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelWeiFenQi> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelWeiFenQi<List<WeiFenQiGoodsModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelWeiFenQi> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
