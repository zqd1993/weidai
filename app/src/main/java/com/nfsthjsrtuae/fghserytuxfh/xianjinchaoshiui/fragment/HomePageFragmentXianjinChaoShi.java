package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiadapter.GoodsItemAdapter1;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.GoodsModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.XianjinChaoShiWebViewActivity;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshipresent.HomePagePresentXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentXianjinChaoShi extends XFragment<HomePagePresentXianjinChaoShi> {

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
    public GoodsItemAdapter1 goodsItemAdapter1;
    public GoodsModelXianjinChaoShi goodsModelXianjinChaoShi, topGoodsModelXianjinChaoShi;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsModelXianjinChaoShi);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_xian_jin_chao_shi;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public HomePagePresentXianjinChaoShi newP() {
        return new HomePagePresentXianjinChaoShi();
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
        if (goodsItemAdapter1 == null) {
            goodsItemAdapter1 = new GoodsItemAdapter1(getActivity());
            goodsItemAdapter1.setRecItemClick(new RecyclerItemCallback<GoodsModelXianjinChaoShi, GoodsItemAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelXianjinChaoShi model, int tag, GoodsItemAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapter1.setHasStableIds(true);
            goodsItemAdapter1.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapter1);
        } else {
            goodsItemAdapter1.setData(mData);
        }
    }

    public void setModel(GoodsModelXianjinChaoShi goodsModelXianjinChaoShi) {
        this.goodsModelXianjinChaoShi = goodsModelXianjinChaoShi;
    }
}
