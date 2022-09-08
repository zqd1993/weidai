package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgapi;


import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BannerModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BaseModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ConfigZhaoCaiAdfmEntity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.DlZhaoCaiAdfmModel;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ProductModelZhaoCaiAdfm;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ZhaoCaiAdfmterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelZhaoCaiAdfm> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelZhaoCaiAdfm<DlZhaoCaiAdfmModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<BaseModelZhaoCaiAdfm<List<ProductModelZhaoCaiAdfm>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<BaseModelZhaoCaiAdfm<List<BannerModelZhaoCaiAdfm>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<BaseModelZhaoCaiAdfm> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelZhaoCaiAdfm<DlZhaoCaiAdfmModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
