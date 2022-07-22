package com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanapi;


import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.BannerModelQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.QuHuaDaiKuanBaseModel;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.ConfigQuHuaDaiKuanEntity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.DlQuHuaDaiKuanModel;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.QuHuaDaiKuanProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface QuHuaDaiKuanInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<QuHuaDaiKuanBaseModel<ConfigQuHuaDaiKuanEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<QuHuaDaiKuanBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<QuHuaDaiKuanBaseModel<ConfigQuHuaDaiKuanEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<QuHuaDaiKuanBaseModel<DlQuHuaDaiKuanModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<QuHuaDaiKuanBaseModel<List<QuHuaDaiKuanProductModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<QuHuaDaiKuanBaseModel<List<BannerModelQuHuaDaiKuan>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<QuHuaDaiKuanBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<QuHuaDaiKuanBaseModel<DlQuHuaDaiKuanModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
