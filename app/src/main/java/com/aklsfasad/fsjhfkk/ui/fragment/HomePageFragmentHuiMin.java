package com.aklsfasad.fsjhfkk.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.adapter.GoodsItemHuiMinAdapter;
import com.aklsfasad.fsjhfkk.model.GoodsHuiMinModel;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.ui.LoginActivityHuiMin;
import com.aklsfasad.fsjhfkk.ui.TanPingActivity;
import com.aklsfasad.fsjhfkk.ui.WebHuiMinActivity;
import com.aklsfasad.fsjhfkk.mvp.XFragment;
import com.aklsfasad.fsjhfkk.present.HomePagePresentHuiMin;
import com.aklsfasad.fsjhfkk.router.Router;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentHuiMin extends XFragment<HomePagePresentHuiMin> {

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

    private Bundle bundle, webBundle;
    private int tag;
    public GoodsItemHuiMinAdapter miaoJieGoodsItemAdapter;
    private GoodsHuiMinModel goodsModel;

    public static HomePageFragmentHuiMin getInstant(int tag) {
        HomePageFragmentHuiMin homePageFragment = new HomePageFragmentHuiMin();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public HomePagePresentHuiMin newP() {
        return new HomePagePresentHuiMin();
    }

    private void productClick(GoodsHuiMinModel model){
        if (model != null) {
            getP().productClick(model);
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
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
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

    public void jumpWebYouXinActivity (GoodsHuiMinModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            StaticUtilHuiMin.getValue((XActivity) getActivity(), WebHuiMinActivity.class, webBundle);
        }
    }

    public void initGoodsItemAdapter(List<GoodsHuiMinModel> mData) {
        if (miaoJieGoodsItemAdapter == null) {
            miaoJieGoodsItemAdapter = new GoodsItemHuiMinAdapter(getActivity());
            miaoJieGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<GoodsHuiMinModel, GoodsItemHuiMinAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsHuiMinModel model, int tag, GoodsItemHuiMinAdapter.ViewHolder holder) {
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

    public void setModel(GoodsHuiMinModel goodsModel){
        this.goodsModel = goodsModel;
    }
}