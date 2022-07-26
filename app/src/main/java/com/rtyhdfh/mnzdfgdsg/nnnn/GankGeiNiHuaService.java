package com.rtyhdfh.mnzdfgdsg.nnnn;


import com.rtyhdfh.mnzdfgdsg.mmmm.BaseRespModelGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.mmmm.ConfigGeiNiHuaModel;
import com.rtyhdfh.mnzdfgdsg.mmmm.GeiNiHuaGoodsModel;
import com.rtyhdfh.mnzdfgdsg.mmmm.GeiNiHuaLoginRespModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankGeiNiHuaService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>> getValue(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelGeiNiHua> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelGeiNiHua<List<GeiNiHuaGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelGeiNiHua> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
