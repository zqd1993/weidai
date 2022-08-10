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
import com.aklsfasad.fsjhfkk.mvp.XFragment;
import com.aklsfasad.fsjhfkk.present.HomePagePresentHuiMin;
import com.aklsfasad.fsjhfkk.present.ProductPresentHuiMin;
import com.aklsfasad.fsjhfkk.ui.WebHuiMinActivity;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductFragmentHuiMin extends XFragment<ProductPresentHuiMin> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_fl)
    public View noDataFl;
    @BindView(R.id.parent_fl)
    View parentFl;

    private Bundle bundle, webBundle;
    private int tag;
    public GoodsItemHuiMinAdapter miaoJieGoodsItemAdapter;
    private GoodsHuiMinModel goodsModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    public ProductPresentHuiMin newP() {
        return new ProductPresentHuiMin();
    }

    private void productClick(GoodsHuiMinModel model){
        if (model != null) {
            getP().productClick(model);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
        parentFl.setOnClickListener(v -> {
            productClick(goodsModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().productList();
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
