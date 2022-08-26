package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoapi;


import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.BaseKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ConfigKuaiJieJieKuanNewVoEntity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.DlKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ProductModelKuaiJieJieKuanNewVo;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceUtilsKuaiJieJieKuanNewVo {

    @GET("/app/config/getConfig")
    Flowable<BaseKuaiJieJieKuanNewVoModel<ConfigKuaiJieJieKuanNewVoEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseKuaiJieJieKuanNewVoModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseKuaiJieJieKuanNewVoModel<ConfigKuaiJieJieKuanNewVoEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseKuaiJieJieKuanNewVoModel<DlKuaiJieJieKuanNewVoModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<BaseKuaiJieJieKuanNewVoModel<List<ProductModelKuaiJieJieKuanNewVo>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseKuaiJieJieKuanNewVoModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseKuaiJieJieKuanNewVoModel<DlKuaiJieJieKuanNewVoModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
