package com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinapi;


import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinm.BaseKouDaiBeiYongJinModel;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinm.ConfigKouDaiBeiYongJinEntity;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinm.DlKouDaiBeiYongJinModel;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinm.ProductKouDaiBeiYongJinModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceKouDaiBeiYongJinUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseKouDaiBeiYongJinModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseKouDaiBeiYongJinModel<DlKouDaiBeiYongJinModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseKouDaiBeiYongJinModel<List<ProductKouDaiBeiYongJinModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseKouDaiBeiYongJinModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseKouDaiBeiYongJinModel<DlKouDaiBeiYongJinModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
