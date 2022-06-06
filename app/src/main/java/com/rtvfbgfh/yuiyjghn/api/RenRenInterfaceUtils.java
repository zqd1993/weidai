package com.rtvfbgfh.yuiyjghn.api;


import com.rtvfbgfh.yuiyjghn.m.RenRenBaseModel;
import com.rtvfbgfh.yuiyjghn.m.RenRenConfigEntity;
import com.rtvfbgfh.yuiyjghn.m.LoginRenRenModel;
import com.rtvfbgfh.yuiyjghn.m.RenRenGoodsModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface RenRenInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<RenRenBaseModel<RenRenConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<RenRenBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<RenRenBaseModel<LoginRenRenModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<RenRenBaseModel<List<RenRenGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<RenRenBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<RenRenBaseModel<LoginRenRenModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
