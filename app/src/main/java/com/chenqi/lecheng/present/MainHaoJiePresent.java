package com.chenqi.lecheng.present;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.model.BaseRespHaoJieModel;
import com.chenqi.lecheng.model.LoginRespHaoJieModel;
import com.chenqi.lecheng.ui.HomePageHaoJieActivity;
import com.chenqi.lecheng.utils.SharedPreferencesHaoJieUtilis;
import com.chenqi.lecheng.utils.StaticHaoJieUtil;
import com.chenqi.lecheng.mvp.XPresent;
import com.chenqi.lecheng.net.ApiHaoJie;
import com.chenqi.lecheng.net.NetError;
import com.chenqi.lecheng.net.XApi;
import com.chenqi.lecheng.net.ApiSubscriber;
import com.google.android.material.snackbar.Snackbar;

public class MainHaoJiePresent extends XPresent<HomePageHaoJieActivity> {

    private String phone, ip;


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

    public void login() {
        if (!TextUtils.isEmpty(SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = SharedPreferencesHaoJieUtilis.getStringFromPref("phone");
            ip = SharedPreferencesHaoJieUtilis.getStringFromPref("ip");
            ApiHaoJie.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespHaoJieModel<LoginRespHaoJieModel>>getApiTransformer())
                    .compose(XApi.<BaseRespHaoJieModel<LoginRespHaoJieModel>>getScheduler())
                    .compose(getV().<BaseRespHaoJieModel<LoginRespHaoJieModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHaoJieModel<LoginRespHaoJieModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticHaoJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespHaoJieModel<LoginRespHaoJieModel> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
        }
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void ioyuuty(Snackbar snackbar, int backgroundColor) {
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
    public static void nmdghdfg(Snackbar snackbar, int messageColor, int backgroundColor) {
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
    public static void qwervdfsf(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

}
