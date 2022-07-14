package com.gdzfgesry.nbfgnwaet.netqianbao;


import com.gdzfgesry.nbfgnwaet.qianbaomodel.BaseRespQianBaoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.QianBaoCompanyInfoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.GoodsQianBaoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.QianBaoLoginRespModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.LoginStatusQianBaoModel;

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

public interface GankQianBaoService {

    @GET("/api/index/is_login")
    Flowable<LoginStatusQianBaoModel> getGankData();

    @GET("/api/index/gs")
    Flowable<BaseRespQianBaoModel<QianBaoCompanyInfoModel>> getCompanyInfo();

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @GET("/api/index/smsapi")
    Flowable<BaseRespQianBaoModel> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Flowable<BaseRespQianBaoModel<QianBaoLoginRespModel>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device, @Query("ip") String ip);

    @GET("/app/user/logins")
    Flowable<BaseRespQianBaoModel<QianBaoLoginRespModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    public static String xnxftry(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ktyizerh(Object o) {

        return toDouble(o, 0);
    }

    public static double ertyhxfh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long fgserzh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @POST("/api/index/alist")
    Flowable<BaseRespQianBaoModel<List<GoodsQianBaoModel>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Flowable<BaseRespQianBaoModel<List<GoodsQianBaoModel>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Flowable<BaseRespQianBaoModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    public static String ouyjdrety(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double etryjhx(Object o) {

        return toDouble(o, 0);
    }

    public static double mdgjsrtys(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long yujxrts(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

}
