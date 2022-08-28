package com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi;


import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBannerModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBaseModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanConfigEntity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanDlModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanProductModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceGeiNiHuaDaiKuanOpNewUtils {

    @GET("/app/config/getConfig")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/product/productList")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanProductModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanBannerModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
