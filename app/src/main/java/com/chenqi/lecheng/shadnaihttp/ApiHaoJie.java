package com.chenqi.lecheng.shadnaihttp;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.utilsshandai.SharedPreferencesHaoJieUtilis;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ApiHaoJie {

    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    private static GankHaoJieService gankHaoJieService;

    public static GankHaoJieService getGankService() {
        if (gankHaoJieService == null && !TextUtils.isEmpty(SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (ApiHaoJie.class) {
                if (gankHaoJieService == null) {
                    gankHaoJieService = XApi.getInstance().getRetrofit(SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL"), true).create(GankHaoJieService.class);
                }
            }
        }
        return gankHaoJieService;
    }

    public static final String PRIVACY_POLICY = "/profile/vohjjk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "/profile/vohjjk/ysxy.html";
    public static String API_BASE_URL = "http://110.42.64.175:7735";

    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void urthfgh(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void nfgsfas(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void ertfvd(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }
}
