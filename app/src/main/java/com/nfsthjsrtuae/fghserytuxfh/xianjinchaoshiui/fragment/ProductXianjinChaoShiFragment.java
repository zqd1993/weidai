package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiadapter.GoodsItemAdapterIXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.GoodsModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshipresent.ProductPresentXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.XianjinChaoShiWebViewActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductXianjinChaoShiFragment extends XFragment<ProductPresentXianjinChaoShi> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterIXianjinChaoShi goodsItemAdapterIXianjinChaoShi;
    private GoodsModelXianjinChaoShi goodsModelXianjinChaoShi;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModelKuaiDianFenQiDai);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_xian_jin_chao_shi;
    }

    @Override
    public ProductPresentXianjinChaoShi newP() {
        return new ProductPresentXianjinChaoShi();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    private void productClick(GoodsModelXianjinChaoShi model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModelXianjinChaoShi model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(XianjinChaoShiWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModelXianjinChaoShi> mData) {
        if (goodsItemAdapterIXianjinChaoShi == null) {
            goodsItemAdapterIXianjinChaoShi = new GoodsItemAdapterIXianjinChaoShi(getActivity());
            goodsItemAdapterIXianjinChaoShi.setRecItemClick(new RecyclerItemCallback<GoodsModelXianjinChaoShi, GoodsItemAdapterIXianjinChaoShi.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelXianjinChaoShi model, int tag, GoodsItemAdapterIXianjinChaoShi.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterIXianjinChaoShi.setHasStableIds(true);
            goodsItemAdapterIXianjinChaoShi.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterIXianjinChaoShi);
        } else {
            goodsItemAdapterIXianjinChaoShi.setData(mData);
        }
    }

    public void setModel(GoodsModelXianjinChaoShi goodsModelXianjinChaoShi) {
        this.goodsModelXianjinChaoShi = goodsModelXianjinChaoShi;
    }
}
