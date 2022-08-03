package com.nfhyrhd.nfhsues.fqbyjnet;


import com.nfhyrhd.nfhsues.fqbyjmodel.BaseRespModelFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjmodel.FQBYJConfigModel;
import com.nfhyrhd.nfhsues.fqbyjmodel.GoodsFQBYJModel;
import com.nfhyrhd.nfhsues.fqbyjmodel.LoginRespModelFQBYJ;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface FQBYJGankService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelFQBYJ<FQBYJConfigModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModelFQBYJ<FQBYJConfigModel>> getValue(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelFQBYJ> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelFQBYJ<LoginRespModelFQBYJ>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelFQBYJ<LoginRespModelFQBYJ>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelFQBYJ<List<GoodsFQBYJModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelFQBYJ> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
