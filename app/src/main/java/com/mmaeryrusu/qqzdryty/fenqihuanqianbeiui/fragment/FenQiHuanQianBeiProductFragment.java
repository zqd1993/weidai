package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.GoodsItemAdapterFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.GoodsFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.mvp.XFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent.ProductPresentFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.router.Router;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.WebViewActivityFenQiHuanQianBei;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class FenQiHuanQianBeiProductFragment extends XFragment<ProductPresentFenQiHuanQianBei> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterFenQiHuanQianBei goodsItemAdapterFenQiHuanQianBei;
    private GoodsFenQiHuanQianBeiModel goodsFenQiHuanQianBeiModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsFenQiHuanQianBeiModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_fen_qi_huan_qian_bei;
    }

    @Override
    public ProductPresentFenQiHuanQianBei newP() {
        return new ProductPresentFenQiHuanQianBei();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    private void productClick(GoodsFenQiHuanQianBeiModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsFenQiHuanQianBeiModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityFenQiHuanQianBei.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsFenQiHuanQianBeiModel> mData) {
        if (goodsItemAdapterFenQiHuanQianBei == null) {
            goodsItemAdapterFenQiHuanQianBei = new GoodsItemAdapterFenQiHuanQianBei(getActivity());
            goodsItemAdapterFenQiHuanQianBei.setRecItemClick(new RecyclerItemCallback<GoodsFenQiHuanQianBeiModel, GoodsItemAdapterFenQiHuanQianBei.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsFenQiHuanQianBeiModel model, int tag, GoodsItemAdapterFenQiHuanQianBei.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterFenQiHuanQianBei.setHasStableIds(true);
            goodsItemAdapterFenQiHuanQianBei.setData(mData);
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterFenQiHuanQianBei);
        } else {
            goodsItemAdapterFenQiHuanQianBei.setData(mData);
        }
    }

    public void setModel(GoodsFenQiHuanQianBeiModel goodsFenQiHuanQianBeiModel) {
        this.goodsFenQiHuanQianBeiModel = goodsFenQiHuanQianBeiModel;
    }
}
