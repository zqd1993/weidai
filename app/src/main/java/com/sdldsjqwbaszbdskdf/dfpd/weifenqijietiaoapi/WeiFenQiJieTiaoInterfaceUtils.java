package com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaoapi;


import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaomodel.BannerWeiFenQiJieTiaoModel;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaomodel.BaseModelWeiFenQiJieTiao;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaomodel.WeiFenQiJieTiaoConfigEntity;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaomodel.WeiFenQiJieTiaoModel;
import com.sdldsjqwbaszbdskdf.dfpd.weifenqijietiaomodel.ProductWeiFenQiJieTiaoModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface WeiFenQiJieTiaoInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelWeiFenQiJieTiao> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                   @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<BaseModelWeiFenQiJieTiao<List<ProductWeiFenQiJieTiaoModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<BaseModelWeiFenQiJieTiao<List<BannerWeiFenQiJieTiaoModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<BaseModelWeiFenQiJieTiao> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
