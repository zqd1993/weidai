package com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia;


import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.BaseRespModelDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.ConfigDKMiaoXiaOpModel;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.DKMiaoXiaOpGoodsModel;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.DKMiaoXiaOpLoginRespModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankDKMiaoXiaOpService {

    @GET("/app/config/getConfig")
    Flowable<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>> getGankData();

    @GET("/app/config/getValue")
    Flowable<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>> getValve(@Query("key") String phone);

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseRespModelDKMiaoXiaOp> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/user/login")
    Flowable<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseRespModelDKMiaoXiaOp<List<DKMiaoXiaOpGoodsModel>>> productList(@Query("mobileType") int mobileType);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelDKMiaoXiaOp> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
