package com.octone.pelicnenwo.huiminjiekuanapi;


import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanBaseModel;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanConfigEntity;
import com.octone.pelicnenwo.huiminjiekuanm.DlModelHuiMinJieKuan;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface HuiMinJieKuanInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<HuiMinJieKuanBaseModel<HuiMinJieKuanConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<HuiMinJieKuanBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<HuiMinJieKuanBaseModel<HuiMinJieKuanConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<HuiMinJieKuanBaseModel<DlModelHuiMinJieKuan>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<HuiMinJieKuanBaseModel<List<HuiMinJieKuanProductModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<HuiMinJieKuanBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<HuiMinJieKuanBaseModel<DlModelHuiMinJieKuan>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
