package com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaadapter.GoodsItemAdapterWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.WanRongXinYongKaGoodsModel;
import com.srysfghsrty.mkdtyusaert.mvp.XFragment;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkapresent.ProductPresentWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.router.Router;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.WebViewWanRongXinYongKaActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductFragment extends XFragment<ProductPresentWanRongXinYongKa> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterWanRongXinYongKa goodsItemAdapterWanRongXinYongKa;
    private WanRongXinYongKaGoodsModel wanRongXinYongKaGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        parentLl.setOnClickListener(v -> {
            productClick(wanRongXinYongKaGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_wan_rong_xin_yong_ka;
    }

    @Override
    public ProductPresentWanRongXinYongKa newP() {
        return new ProductPresentWanRongXinYongKa();
    }

    private void productClick(WanRongXinYongKaGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(WanRongXinYongKaGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewWanRongXinYongKaActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<WanRongXinYongKaGoodsModel> mData) {
        if (goodsItemAdapterWanRongXinYongKa == null) {
            goodsItemAdapterWanRongXinYongKa = new GoodsItemAdapterWanRongXinYongKa(getActivity());
            goodsItemAdapterWanRongXinYongKa.setRecItemClick(new RecyclerItemCallback<WanRongXinYongKaGoodsModel, GoodsItemAdapterWanRongXinYongKa.ViewHolder>() {
                @Override
                public void onItemClick(int position, WanRongXinYongKaGoodsModel model, int tag, GoodsItemAdapterWanRongXinYongKa.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterWanRongXinYongKa.setHasStableIds(true);
            goodsItemAdapterWanRongXinYongKa.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterWanRongXinYongKa);
        } else {
            goodsItemAdapterWanRongXinYongKa.setData(mData);
        }
    }

    public void setModel(WanRongXinYongKaGoodsModel wanRongXinYongKaGoodsModel) {
        this.wanRongXinYongKaGoodsModel = wanRongXinYongKaGoodsModel;
    }
}
