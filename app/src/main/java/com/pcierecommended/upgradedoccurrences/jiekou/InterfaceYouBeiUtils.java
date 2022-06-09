package com.pcierecommended.upgradedoccurrences.jiekou;


import com.pcierecommended.upgradedoccurrences.moxing.BaseYouBeiModel;
import com.pcierecommended.upgradedoccurrences.moxing.ConfigYouBeiEntity;
import com.pcierecommended.upgradedoccurrences.moxing.DlYouBeiModel;
import com.pcierecommended.upgradedoccurrences.moxing.ProductYouBeiModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceYouBeiUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseYouBeiModel<ConfigYouBeiEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseYouBeiModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseYouBeiModel<DlYouBeiModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseYouBeiModel<List<ProductYouBeiModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseYouBeiModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseYouBeiModel<DlYouBeiModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
