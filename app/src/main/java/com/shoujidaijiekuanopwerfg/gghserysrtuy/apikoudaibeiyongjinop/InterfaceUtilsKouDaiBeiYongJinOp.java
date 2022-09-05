package com.shoujidaijiekuanopwerfg.gghserysrtuy.apikoudaibeiyongjinop;


import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.BaseKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ConfigKouDaiBeiYongJinOpEntity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.DlKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ProductModelKouDaiBeiYongJinOp;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsKouDaiBeiYongJinOp {

    @GET("/app/config/getConfig")
    Flowable<BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseKouDaiBeiYongJinOpModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseKouDaiBeiYongJinOpModel<DlKouDaiBeiYongJinOpModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<BaseKouDaiBeiYongJinOpModel<List<ProductModelKouDaiBeiYongJinOp>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseKouDaiBeiYongJinOpModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseKouDaiBeiYongJinOpModel<DlKouDaiBeiYongJinOpModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
