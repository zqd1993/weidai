package com.bghfr.yrtweb.present;

import com.bghfr.yrtweb.model.BaseModel;
import com.bghfr.yrtweb.model.LoginModel;
import com.bghfr.yrtweb.ui.MainActivity;
import com.bghfr.yrtweb.utils.SPUtilis;
import com.bghfr.yrtweb.utils.MainUtil;
import com.bghfr.yrtweb.mvp.XPresent;
import com.bghfr.yrtweb.net.Api;
import com.bghfr.yrtweb.net.NetError;
import com.bghfr.yrtweb.net.XApi;
import com.bghfr.yrtweb.net.ApiSubscriber;

import java.text.DateFormat;
import java.util.Date;

public class MainHuiMin extends XPresent<MainActivity> {

    private String phone, ip;

    public void login() {
        phone = SPUtilis.getStringFromPref("phone");
        ip = SPUtilis.getStringFromPref("ip");
        Api.getGankService().logins(phone, ip)
                .compose(XApi.<BaseModel<LoginModel>>getApiTransformer())
                .compose(XApi.<BaseModel<LoginModel>>getScheduler())
                .compose(getV().<BaseModel<LoginModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<LoginModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        MainUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseModel<LoginModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(final String time) {
        return null;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
//        try {
//            return format.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return null;
    }


}
