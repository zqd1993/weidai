package com.rihdkauecgh.plihgnytrvfws.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.adapter.GoodsItemAdapter;
import com.rihdkauecgh.plihgnytrvfws.model.GoodsModel;
import com.rihdkauecgh.plihgnytrvfws.ui.WebViewActivity;
import com.rihdkauecgh.plihgnytrvfws.mvp.XFragment;
import com.rihdkauecgh.plihgnytrvfws.present.HomePagePresent;
import com.rihdkauecgh.plihgnytrvfws.router.Router;

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
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.click_view)
    View click_view;
    @BindView(R.id.click_view_1)
    View click_view_1;

    private Bundle webBundle;
    public GoodsItemAdapter goodsItemAdapter;
    private GoodsModel goodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        productBg.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        swipeRefreshLayout.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        click_view.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        click_view_1.setOnClickListener(v -> {
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

    private void productClick(GoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModel> mData) {
        if (goodsItemAdapter == null) {
            goodsItemAdapter = new GoodsItemAdapter(getActivity());
            goodsItemAdapter.setRecItemClick(new RecyclerItemCallback<GoodsModel, GoodsItemAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModel model, int tag, GoodsItemAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapter.setHasStableIds(true);
            goodsItemAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapter);
        } else {
            goodsItemAdapter.setData(mData);
        }
    }

    public void setModel(GoodsModel goodsModel) {
        this.goodsModel = goodsModel;
    }
}
