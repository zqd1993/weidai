package com.nsryryasdt.ioerdfjrtu.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.adapter.GoodsItemAdapter;
import com.nsryryasdt.ioerdfjrtu.model.GoodsModel;
import com.nsryryasdt.ioerdfjrtu.ui.WebViewActivity;
import com.nsryryasdt.ioerdfjrtu.mvp.XFragment;
import com.nsryryasdt.ioerdfjrtu.present.HomePagePresent;
import com.nsryryasdt.ioerdfjrtu.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragment extends XFragment<HomePagePresent> {

    @BindView(R.id.product_bg)
    View productBg;
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
    @BindView(R.id.money_num_tv)
    public TextView money_num_tv;
    @BindView(R.id.click_view_1)
    View click_view_1;

    private Bundle webBundle;
    public GoodsItemAdapter goodsItemAdapter;
    public GoodsModel goodsModel, topGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
