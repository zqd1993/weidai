package com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan;


import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.BaseModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ConfigEntityLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.DlModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ProductLingHuoJieHuanModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsLingHuoJieHuan {

    @GET("/app/config/getConfig")
    Flowable<BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelLingHuoJieHuan> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelLingHuoJieHuan<DlModelLingHuoJieHuan>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseModelLingHuoJieHuan<List<ProductLingHuoJieHuanModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseModelLingHuoJieHuan> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelLingHuoJieHuan<DlModelLingHuoJieHuan>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
