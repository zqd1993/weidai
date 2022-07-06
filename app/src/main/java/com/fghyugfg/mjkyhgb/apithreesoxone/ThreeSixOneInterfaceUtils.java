package com.fghyugfg.mjkyhgb.apithreesoxone;


import com.fghyugfg.mjkyhgb.threesoxonem.BaseThreeSixOneModel;
import com.fghyugfg.mjkyhgb.threesoxonem.ConfigThreeSixOneEntity;
import com.fghyugfg.mjkyhgb.threesoxonem.ThreeSixOneDlModel;
import com.fghyugfg.mjkyhgb.threesoxonem.ProductModelThreeSixOne;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ThreeSixOneInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseThreeSixOneModel<ConfigThreeSixOneEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseThreeSixOneModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseThreeSixOneModel<ThreeSixOneDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseThreeSixOneModel<List<ProductModelThreeSixOne>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseThreeSixOneModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseThreeSixOneModel<ThreeSixOneDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
