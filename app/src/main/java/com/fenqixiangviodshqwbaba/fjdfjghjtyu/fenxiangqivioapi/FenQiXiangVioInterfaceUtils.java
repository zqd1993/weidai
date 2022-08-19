package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioapi;


import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioBaseModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioConfigEntity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.DlFenQiXiangVioModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.ProductModelFenQiXiangVio;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface FenQiXiangVioInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<FenQiXiangVioBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<FenQiXiangVioBaseModel<DlFenQiXiangVioModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<FenQiXiangVioBaseModel<List<ProductModelFenQiXiangVio>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<FenQiXiangVioBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<FenQiXiangVioBaseModel<DlFenQiXiangVioModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
