package com.akjsdhfkjhj.kahssj.present;

import android.text.TextUtils;

import com.akjsdhfkjhj.kahssj.model.BaseModel;
import com.akjsdhfkjhj.kahssj.model.LoginModel;
import com.akjsdhfkjhj.kahssj.ui.MainActivity;
import com.akjsdhfkjhj.kahssj.utils.SPUtilis;
import com.akjsdhfkjhj.kahssj.utils.MainUtil;
import com.akjsdhfkjhj.kahssj.mvp.XPresent;
import com.akjsdhfkjhj.kahssj.net.Api;
import com.akjsdhfkjhj.kahssj.net.NetError;
import com.akjsdhfkjhj.kahssj.net.XApi;
import com.akjsdhfkjhj.kahssj.net.ApiSubscriber;

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
