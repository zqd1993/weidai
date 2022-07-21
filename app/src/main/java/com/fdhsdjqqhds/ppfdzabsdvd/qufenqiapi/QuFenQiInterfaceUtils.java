package com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi;


import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.BannerModelQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.BaseQuFenQiModel;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.QuFenQiConfigEntity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.DlQuFenQiModel;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.ProductModelQuFenQi;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface QuFenQiInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseQuFenQiModel<QuFenQiConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseQuFenQiModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseQuFenQiModel<QuFenQiConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseQuFenQiModel<DlQuFenQiModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseQuFenQiModel<List<ProductModelQuFenQi>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<BaseQuFenQiModel<List<BannerModelQuFenQi>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<BaseQuFenQiModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseQuFenQiModel<DlQuFenQiModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
