package com.chenqi.lecheng.shandaipage.activityshandai;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenqi.lecheng.utilsshandai.SharedPreferencesHaoJieUtilis;
import com.chenqi.lecheng.utilsshandai.StaticHaoJieUtil;
import com.google.android.material.snackbar.Snackbar;
import com.lihang.ShadowLayout;
import com.chenqi.lecheng.R;
import com.chenqi.lecheng.mvp.XActivity;
import com.chenqi.lecheng.shadnaihttp.ApiHaoJie;
import com.chenqi.lecheng.router.Router;
import com.chenqi.lecheng.utilsshandai.StatusBarHaoJieUtil;

import butterknife.BindView;

public class MoreInfoHaoJieActivity extends XActivity {

    @BindView(R.id.app_info_sl)
    ShadowLayout appInfoSl;
    @BindView(R.id.zcxy_sl)
    ShadowLayout zcxySl;
    @BindView(R.id.ysxy_sl)
    ShadowLayout ysxySl;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.biaoti_tv)
    TextView biaotiTv;

    private Bundle bundle;


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarHaoJieUtil.setTransparent(this, false);
        if (SharedPreferencesHaoJieUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backIv.setOnClickListener(v -> finish());
        biaotiTv.setText("更多信息");
        appInfoSl.setOnClickListener(v -> {
            StaticHaoJieUtil.getValue(this, AboutHaoJieActivity.class, null);
        });
        zcxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", ApiHaoJie.PRIVACY_POLICY);
            StaticHaoJieUtil.getValue(this, HaoJieWebActivity.class, bundle);
        });
        ysxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", ApiHaoJie.USER_SERVICE_AGREEMENT);
            StaticHaoJieUtil.getValue(this, HaoJieWebActivity.class, bundle);
        });
    }


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void mfgujty(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void bfssdf(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void rwefsd(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_more_info_haojie;
    }


    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void mghsfdg(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void nfafefe(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void bfdagrt(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

    @Override
    public Object newP() {
        return null;
    }
}
