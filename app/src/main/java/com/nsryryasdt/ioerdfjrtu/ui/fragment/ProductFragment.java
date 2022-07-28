package com.nsryryasdt.ioerdfjrtu.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.adapter.GoodsItemAdapter;
import com.nsryryasdt.ioerdfjrtu.model.GoodsModel;
import com.nsryryasdt.ioerdfjrtu.mvp.XFragment;
import com.nsryryasdt.ioerdfjrtu.present.ProductPresent;
import com.nsryryasdt.ioerdfjrtu.router.Router;
import com.nsryryasdt.ioerdfjrtu.ui.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductFragment extends XFragment<ProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapter goodsItemAdapter;
    private GoodsModel goodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    public ProductPresent newP() {
        return new ProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
