package com.dshqwbabasmdsncx.fjdfj.andinjiedaiapi;


import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.BaseModelAnXinJieDai;
import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.AnXinJieDaiConfigEntity;
import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.DlModelAnXinJieDai;
import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.ProductAnXinJieDaiModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface InterfaceAnXinJieDaiUtils {

    @GET("/app/config/getConfig")
    Flowable<BaseModelAnXinJieDai<AnXinJieDaiConfigEntity>> getConfig();

    @GET("/app/user/sendVerifyCode")
    Flowable<BaseModelAnXinJieDai> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/config/getValue")
    Flowable<BaseModelAnXinJieDai<AnXinJieDaiConfigEntity>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Flowable<BaseModelAnXinJieDai<DlModelAnXinJieDai>> login(@Query("phone") String phone, @Query("code") String code
            , @Query("device") String device, @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productList")
    Flowable<BaseModelAnXinJieDai<List<ProductAnXinJieDaiModel>>> productList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Flowable<BaseModelAnXinJieDai> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/user/logins")
    Flowable<BaseModelAnXinJieDai<DlModelAnXinJieDai>> logins(@Query("phone") String phone, @Query("ip") String ip);

}
