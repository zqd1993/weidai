package com.dlproject.jxdk.lejieqianbaoapi;


import com.dlproject.jxdk.lejieqianbaom.BaseModelLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.ConfigEntityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.DlModelLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.ProductModelLeJieQianBao;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface LeJieQianBaoInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelLeJieQianBao<ConfigEntityLeJieQianBao>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelLeJieQianBao> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelLeJieQianBao<ConfigEntityLeJieQianBao>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelLeJieQianBao<DlModelLeJieQianBao>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseModelLeJieQianBao> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelLeJieQianBao<DlModelLeJieQianBao>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
