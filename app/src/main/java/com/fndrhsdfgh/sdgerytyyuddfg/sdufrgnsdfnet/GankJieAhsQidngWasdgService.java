package com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet;


import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.BaseRespModelJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.ConfigJieAhsQidngWasdgModel;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.JieAhsQidngWasdgGoodsModel;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.LoginRespJieAhsQidngWasdgModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankJieAhsQidngWasdgService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>> getValve(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelJieAhsQidngWasdg> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelJieAhsQidngWasdg<LoginRespJieAhsQidngWasdgModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device
            , @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/logins")
    Flowable<BaseRespModelJieAhsQidngWasdg<LoginRespJieAhsQidngWasdgModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelJieAhsQidngWasdg<List<JieAhsQidngWasdgGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelJieAhsQidngWasdg> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
