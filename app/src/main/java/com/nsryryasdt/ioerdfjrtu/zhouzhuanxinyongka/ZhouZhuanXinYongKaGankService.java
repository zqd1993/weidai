package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka;


import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.BaseRespModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.CompanyInfoZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.GoodsZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.LoginRespZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.LoginStatusModelZhouZhuanXinYongKa;

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

public interface ZhouZhuanXinYongKaGankService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusModelZhouZhuanXinYongKa> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Flowable<BaseRespModelZhouZhuanXinYongKa> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Flowable<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespModelZhouZhuanXinYongKa> productClick(@Query("productId") long productId, @Query("phone") String phone);

}
