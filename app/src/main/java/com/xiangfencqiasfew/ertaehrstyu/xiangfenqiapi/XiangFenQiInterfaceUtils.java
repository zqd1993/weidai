package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiapi;


import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BannerModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BaseModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ConfigXiangFenQiEntity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.DlXiangFenQiModel;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ProductModelXiangFenQi;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface XiangFenQiInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelXiangFenQi<ConfigXiangFenQiEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelXiangFenQi> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelXiangFenQi<ConfigXiangFenQiEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelXiangFenQi<DlXiangFenQiModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModelXiangFenQi<List<ProductModelXiangFenQi>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<BaseModelXiangFenQi<List<BannerModelXiangFenQi>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<BaseModelXiangFenQi> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelXiangFenQi<DlXiangFenQiModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
