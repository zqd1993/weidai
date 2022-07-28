package com.fjsdkqwj.pfdioewjnsd.apimeifenqi;


import com.fjsdkqwj.pfdioewjnsd.mmeifenqi.BaseModelMeiFenQi;
import com.fjsdkqwj.pfdioewjnsd.mmeifenqi.ConfigMeiFenQiEntity;
import com.fjsdkqwj.pfdioewjnsd.mmeifenqi.MeiFenQiDlModel;
import com.fjsdkqwj.pfdioewjnsd.mmeifenqi.ProductModelMeiFenQi;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceMeiFenQiUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelMeiFenQi<ConfigMeiFenQiEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelMeiFenQi> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelMeiFenQi<ConfigMeiFenQiEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelMeiFenQi<MeiFenQiDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModelMeiFenQi<List<ProductModelMeiFenQi>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseModelMeiFenQi> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelMeiFenQi<MeiFenQiDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
