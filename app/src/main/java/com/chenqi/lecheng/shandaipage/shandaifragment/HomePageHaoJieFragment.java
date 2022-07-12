package com.chenqi.lecheng.shandaipage.shandaifragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.adaptershandai.GoodsItemAdapterHaoJie;
import com.chenqi.lecheng.shadnaimodel.GoodsWinAHaoJieModel;
import com.chenqi.lecheng.shandaipage.activityshandai.HaoJieWebActivity;
import com.chenqi.lecheng.mvp.XFragment;
import com.chenqi.lecheng.presentshandai.HomePageHaoJiePresent;
import com.chenqi.lecheng.router.Router;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageHaoJieFragment extends XFragment<HomePageHaoJiePresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_fl)
    public View noDataFl;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;


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

    private Bundle bundle, webBundle;
    private int tag;
    public GoodsItemAdapterHaoJie miaoJieGoodsItemAdapter;
    private GoodsWinAHaoJieModel goodsModel;

    public static HomePageHaoJieFragment getInstant(int tag) {
        HomePageHaoJieFragment homePageFragment = new HomePageHaoJieFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void nmhdhsfdg(Snackbar snackbar, int backgroundColor) {
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
    public static void bfgsher(Snackbar snackbar, int messageColor, int backgroundColor) {
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
    public static void cwersdf(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_haojie;
    }

    @Override
    public HomePageHaoJiePresent newP() {
        return new HomePageHaoJiePresent();
    }

    private void productClick(GoodsWinAHaoJieModel model) {
        if (model != null) {
            getP().goodsClick(model);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt("tag");
            if (tag == 1) {
                productBg.setVisibility(View.GONE);
                homePageBg.setVisibility(View.VISIBLE);
            } else {
                productBg.setVisibility(View.VISIBLE);
                homePageBg.setVisibility(View.GONE);
            }
        }
        new Handler().postDelayed(() -> {
            getP().getList();
        }, 200);
        swipeRefreshLayout.setOnRefreshListener(() -> getP().getList());
        noDataFl.setOnClickListener(v -> getP().getList());
        productBg.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(goodsModel);
        });
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void utyhfgh(Snackbar snackbar, int backgroundColor) {
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
    public static void awsadvx(Snackbar snackbar, int messageColor, int backgroundColor) {
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
    public static void basdwer(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    public void jumpWebYouXinActivity(GoodsWinAHaoJieModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            Router.newIntent(getActivity())
                    .to(HaoJieWebActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsWinAHaoJieModel> mData) {
        if (miaoJieGoodsItemAdapter == null) {
            miaoJieGoodsItemAdapter = new GoodsItemAdapterHaoJie(getActivity());
            miaoJieGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<GoodsWinAHaoJieModel, GoodsItemAdapterHaoJie.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsWinAHaoJieModel model, int tag, GoodsItemAdapterHaoJie.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            miaoJieGoodsItemAdapter.setHasStableIds(true);
            miaoJieGoodsItemAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(miaoJieGoodsItemAdapter);
        } else {
            miaoJieGoodsItemAdapter.setData(mData);
        }
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void ityufghfg(Snackbar snackbar, int backgroundColor) {
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
    public static void bdsdf(Snackbar snackbar, int messageColor, int backgroundColor) {
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
    public static void werfdvsd(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    public void setModel(GoodsWinAHaoJieModel goodsModel) {
        this.goodsModel = goodsModel;
    }
}
