package com.qwerxcvdf.ytubvcbfg.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qwerxcvdf.ytubvcbfg.R;
import com.qwerxcvdf.ytubvcbfg.adapter.MiaoJieGoodsItemAdapter;
import com.qwerxcvdf.ytubvcbfg.model.GoodsModel;
import com.qwerxcvdf.ytubvcbfg.ui.WebViewActivity;
import com.qwerxcvdf.ytubvcbfg.mvp.XFragment;
import com.qwerxcvdf.ytubvcbfg.present.HomePagePresent;
import com.qwerxcvdf.ytubvcbfg.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragment extends XFragment<HomePagePresent> {

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
    public MiaoJieGoodsItemAdapter miaoJieGoodsItemAdapter;
    private GoodsModel goodsModel;

    public static HomePageFragment getInstant(int tag) {
        HomePageFragment homePageFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public HomePagePresent newP() {
        return new HomePagePresent();
    }

    private void productClick(GoodsModel model){
        if (model != null) {
            getP().productClick(model);
        }
    }

    public void jumpWebActivity (GoodsModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            Router.newIntent(getActivity())
                    .to(WebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModel> mData) {
        if (miaoJieGoodsItemAdapter == null) {
            miaoJieGoodsItemAdapter = new MiaoJieGoodsItemAdapter(getActivity());
            miaoJieGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<GoodsModel, MiaoJieGoodsItemAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModel model, int tag, MiaoJieGoodsItemAdapter.ViewHolder holder) {
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

    public void setModel(GoodsModel goodsModel){
        this.goodsModel = goodsModel;
    }
}
