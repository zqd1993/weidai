package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaapi;


import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.KuaiJieKuanBaseModel;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.ConfigEntityKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.DlKuaiJieKuanModel;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.ProductKuaiJieKuanModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsKuaiJieKuan {

    @GET("/app/config/getConfig")
    Flowable<KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<KuaiJieKuanBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<KuaiJieKuanBaseModel<DlKuaiJieKuanModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<KuaiJieKuanBaseModel<List<ProductKuaiJieKuanModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<KuaiJieKuanBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<KuaiJieKuanBaseModel<DlKuaiJieKuanModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
