package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwapi;


import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.BaseTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ConfigTiQianHuaDaikuanHwEntity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.DlTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ProductModelTiQianHuaDaikuanHw;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsTiQianHuaDaikuanHw {

    @GET("/app/config/getConfig")
    Flowable<BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseTiQianHuaDaikuanHwModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseTiQianHuaDaikuanHwModel<DlTiQianHuaDaikuanHwModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseTiQianHuaDaikuanHwModel<List<ProductModelTiQianHuaDaikuanHw>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseTiQianHuaDaikuanHwModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseTiQianHuaDaikuanHwModel<DlTiQianHuaDaikuanHwModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
