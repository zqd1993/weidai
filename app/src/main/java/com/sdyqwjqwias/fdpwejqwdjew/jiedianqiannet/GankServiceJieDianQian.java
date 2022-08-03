package com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet;


import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.BaseRespModelJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.ConfigJieDianQianModel;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.JieDianQianGoodsModel;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.LoginRespModelJieDianQian;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankServiceJieDianQian {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelJieDianQian<ConfigJieDianQianModel>> getGankData();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelJieDianQian> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelJieDianQian<LoginRespModelJieDianQian>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelJieDianQian<LoginRespModelJieDianQian>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelJieDianQian<List<JieDianQianGoodsModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelJieDianQian> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseRespModelJieDianQian<ConfigJieDianQianModel>> getValve(@Query("key") String phone);

}
