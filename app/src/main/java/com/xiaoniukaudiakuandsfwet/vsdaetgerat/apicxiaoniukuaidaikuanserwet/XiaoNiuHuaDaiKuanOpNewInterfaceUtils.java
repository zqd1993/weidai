package com.xiaoniukaudiakuandsfwet.vsdaetgerat.apicxiaoniukuaidaikuanserwet;


import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBannerModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBaseModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewConfigEntity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewDlModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface XiaoNiuHuaDaiKuanOpNewInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewProductModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewBannerModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
