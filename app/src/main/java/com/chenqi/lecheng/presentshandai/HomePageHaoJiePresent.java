package com.chenqi.lecheng.presentshandai;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.shadnaimodel.BaseRespHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.GoodsWinAHaoJieModel;
import com.chenqi.lecheng.utilsshandai.SharedPreferencesHaoJieUtilis;
import com.chenqi.lecheng.utilsshandai.StaticHaoJieUtil;
import com.chenqi.lecheng.mvp.XPresent;
import com.chenqi.lecheng.shadnaihttp.ApiHaoJie;
import com.chenqi.lecheng.shadnaihttp.ApiSubscriber;
import com.chenqi.lecheng.shadnaihttp.NetError;
import com.chenqi.lecheng.shadnaihttp.XApi;
import com.chenqi.lecheng.shandaipage.shandaifragment.HomePageHaoJieFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class HomePageHaoJiePresent extends XPresent<HomePageHaoJieFragment> {

    private int mobileType;

    private String phone;


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

    public void getList() {
            mobileType = SharedPreferencesHaoJieUtilis.getIntFromPref("mobileType");
            phone = SharedPreferencesHaoJieUtilis.getStringFromPref("phone");
            getV().goodsModel = null;
            ApiHaoJie.getGankService().productList(mobileType, phone)
                    .compose(XApi.<BaseRespHaoJieModel<List<GoodsWinAHaoJieModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespHaoJieModel<List<GoodsWinAHaoJieModel>>>getScheduler())
                    .compose(getV().<BaseRespHaoJieModel<List<GoodsWinAHaoJieModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHaoJieModel<List<GoodsWinAHaoJieModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().miaoJieGoodsItemAdapter == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticHaoJieUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespHaoJieModel<List<GoodsWinAHaoJieModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            } else {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                        }
                    });
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void mghfghg(Snackbar snackbar, int backgroundColor) {
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
    public static void rtyhgf(Snackbar snackbar, int messageColor, int backgroundColor) {
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
    public static void bdfaer(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    public void goodsClick(GoodsWinAHaoJieModel model) {
            phone = SharedPreferencesHaoJieUtilis.getStringFromPref("phone");
            ApiHaoJie.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespHaoJieModel>getApiTransformer())
                    .compose(XApi.<BaseRespHaoJieModel>getScheduler())
                    .compose(getV().<BaseRespHaoJieModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHaoJieModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebYouXinActivity(model);
                            StaticHaoJieUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespHaoJieModel gankResults) {
                            getV().jumpWebYouXinActivity(model);
                        }
                    });
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void mghfhfgh(Snackbar snackbar, int backgroundColor) {
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
    public static void werdgdfg(Snackbar snackbar, int messageColor, int backgroundColor) {
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
    public static void bndzgsdf(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

}
