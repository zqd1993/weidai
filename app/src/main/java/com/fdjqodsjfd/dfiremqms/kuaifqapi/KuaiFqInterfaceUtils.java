package com.fdjqodsjfd.dfiremqms.kuaifqapi;


import com.fdjqodsjfd.dfiremqms.kuaifqm.BannerKuaiFqModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqBaseModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.ConfigEntitKuaiFqy;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqDlModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.ProductKuaiFqModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface KuaiFqInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<KuaiFqBaseModel<ConfigEntitKuaiFqy>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<KuaiFqBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<KuaiFqBaseModel<ConfigEntitKuaiFqy>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<KuaiFqBaseModel<KuaiFqDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<KuaiFqBaseModel<List<ProductKuaiFqModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<KuaiFqBaseModel<List<BannerKuaiFqModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<KuaiFqBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<KuaiFqBaseModel<KuaiFqDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
