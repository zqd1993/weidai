package com.qwbasvsd.zmnxcmdsjsdk.lefenqiapi;


import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.BannerLeFenQiNewsModel;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.BaseModelLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.LeFenQiNewsConfigEntity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.DlLeFenQiNewsModel;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.ProductLeFenQiNewsModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface LeFenQiNewsInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelLeFenQiNews<LeFenQiNewsConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelLeFenQiNews> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelLeFenQiNews<LeFenQiNewsConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelLeFenQiNews<DlLeFenQiNewsModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModelLeFenQiNews<List<ProductLeFenQiNewsModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<BaseModelLeFenQiNews<List<BannerLeFenQiNewsModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<BaseModelLeFenQiNews> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelLeFenQiNews<DlLeFenQiNewsModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
