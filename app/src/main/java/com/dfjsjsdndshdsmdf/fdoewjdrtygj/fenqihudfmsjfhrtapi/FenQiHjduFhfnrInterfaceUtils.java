package com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtapi;


import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.BannerFenQiHjduFhfnrModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.FenQiHjduFhfnrBaseModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.ConfigFenQiHjduFhfnrEntity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.FenQiHjduFhfnrDlModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.ProductFenQiHjduFhfnrModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface FenQiHjduFhfnrInterfaceUtils {

    @GET("/app/config/getConfig")
    Flowable<FenQiHjduFhfnrBaseModel<ConfigFenQiHjduFhfnrEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<FenQiHjduFhfnrBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<FenQiHjduFhfnrBaseModel<ConfigFenQiHjduFhfnrEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<FenQiHjduFhfnrBaseModel<FenQiHjduFhfnrDlModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip
            , @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<FenQiHjduFhfnrBaseModel<List<ProductFenQiHjduFhfnrModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/bannerList")
    Flowable<FenQiHjduFhfnrBaseModel<List<BannerFenQiHjduFhfnrModel>>> bannerList();

    @GET("/app/product/productClick")
    Flowable<FenQiHjduFhfnrBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<FenQiHjduFhfnrBaseModel<FenQiHjduFhfnrDlModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
