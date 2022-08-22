package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet;


import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.SuiJieXinYongKaBaseRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.CompanyInfoModelSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.GoodsSuiJieXinYongKaModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.LoginSuiJieXinYongKaRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.LoginStatusModelSuiJieXinYongKa;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankServiceSuiJieXinYongKa {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModelSuiJieXinYongKa> getGankData();

    @GET("/api/index/gs")
    Flowable<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<SuiJieXinYongKaBaseRespModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<SuiJieXinYongKaBaseRespModel<List<GoodsSuiJieXinYongKaModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<SuiJieXinYongKaBaseRespModel<List<GoodsSuiJieXinYongKaModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<SuiJieXinYongKaBaseRespModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
