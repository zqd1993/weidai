package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeifragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.GoodsItemAdapterFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.FenQiHuanQianBeiGoodsModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.FenQiHuanQianBeiWebViewActivity;
import com.mmaeryrusu.qqzdryty.mvp.XFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent.ProductPresentFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.router.Router;

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
    private FenQiHuanQianBeiGoodsModel fenQiHuanQianBeiGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_qi_huan_qian_product;
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

    private void productClick(FenQiHuanQianBeiGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(FenQiHuanQianBeiGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(FenQiHuanQianBeiWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<FenQiHuanQianBeiGoodsModel> mData) {
        if (goodsItemAdapterFenQiHuanQianBei == null) {
            goodsItemAdapterFenQiHuanQianBei = new GoodsItemAdapterFenQiHuanQianBei(getActivity());
            goodsItemAdapterFenQiHuanQianBei.setRecItemClick(new RecyclerItemCallback<FenQiHuanQianBeiGoodsModel, GoodsItemAdapterFenQiHuanQianBei.ViewHolder>() {
                @Override
                public void onItemClick(int position, FenQiHuanQianBeiGoodsModel model, int tag, GoodsItemAdapterFenQiHuanQianBei.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterFenQiHuanQianBei.setHasStableIds(true);
            goodsItemAdapterFenQiHuanQianBei.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterFenQiHuanQianBei);
        } else {
            goodsItemAdapterFenQiHuanQianBei.setData(mData);
        }
    }

    public void setModel(FenQiHuanQianBeiGoodsModel fenQiHuanQianBeiGoodsModel) {
        this.fenQiHuanQianBeiGoodsModel = fenQiHuanQianBeiGoodsModel;
    }
}
