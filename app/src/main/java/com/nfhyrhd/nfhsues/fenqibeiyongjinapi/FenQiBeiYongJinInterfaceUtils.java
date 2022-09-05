package com.nfhyrhd.nfhsues.fenqibeiyongjinapi;


import com.nfhyrhd.nfhsues.fenqibeiyongjinm.FenQiBeiYongJinBaseModel;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.ConfigEntityFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.DlFenQiBeiYongJinModel;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.ProductModelFenQiBeiYongJin;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface FenQiBeiYongJinInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<FenQiBeiYongJinBaseModel<ConfigEntityFenQiBeiYongJin>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<FenQiBeiYongJinBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<FenQiBeiYongJinBaseModel<ConfigEntityFenQiBeiYongJin>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<FenQiBeiYongJinBaseModel<DlFenQiBeiYongJinModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<FenQiBeiYongJinBaseModel<List<ProductModelFenQiBeiYongJin>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<FenQiBeiYongJinBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<FenQiBeiYongJinBaseModel<DlFenQiBeiYongJinModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
