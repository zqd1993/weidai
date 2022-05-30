package com.werwerd.ertegdfg.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.adapter.GoodsItemAdapterYouXin;
import com.werwerd.ertegdfg.model.GoodsWinAModel;
import com.werwerd.ertegdfg.ui.WebActivity;
import com.werwerd.ertegdfg.mvp.XFragment;
import com.werwerd.ertegdfg.present.HomePageYouXinPresent;
import com.werwerd.ertegdfg.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageYouXinFragment extends XFragment<HomePageYouXinPresent> {

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
    public GoodsItemAdapterYouXin miaoJieGoodsItemAdapter;
    private GoodsWinAModel goodsModel;

    public static HomePageYouXinFragment getInstant(int tag) {
        HomePageYouXinFragment homePageFragment = new HomePageYouXinFragment();
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
    public HomePageYouXinPresent newP() {
        return new HomePageYouXinPresent();
    }

    private void productClick(GoodsWinAModel model){
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

    public void jumpWebYouXinActivity (GoodsWinAModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            Router.newIntent(getActivity())
                    .to(WebActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsWinAModel> mData) {
        if (miaoJieGoodsItemAdapter == null) {
            miaoJieGoodsItemAdapter = new GoodsItemAdapterYouXin(getActivity());
            miaoJieGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<GoodsWinAModel, GoodsItemAdapterYouXin.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsWinAModel model, int tag, GoodsItemAdapterYouXin.ViewHolder holder) {
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

    public void setModel(GoodsWinAModel goodsModel){
        this.goodsModel = goodsModel;
    }
}
