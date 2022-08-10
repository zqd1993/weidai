package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.GoodsItemAdapterFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.FenQiHuanQianBeiGoodsModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.FenQiHuanQianBeiWebViewActivity;
import com.mmaeryrusu.qqzdryty.mvp.XFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent.HomePageFenQiHuanQianBeiPresent;
import com.mmaeryrusu.qqzdryty.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFenQiHuanQianBeiFragment extends XFragment<HomePageFenQiHuanQianBeiPresent> {

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
    @BindView(R.id.money_num_tv)
    public TextView money_num_tv;
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.info_tv)
    public TextView info_tv;

    private Bundle webBundle;
    public GoodsItemAdapterFenQiHuanQianBei goodsItemAdapterFenQiHuanQianBei;
    public FenQiHuanQianBeiGoodsModel fenQiHuanQianBeiGoodsModel, topFenQiHuanQianBeiGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topFenQiHuanQianBeiGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_qi_huan_qian_home_page;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public HomePageFenQiHuanQianBeiPresent newP() {
        return new HomePageFenQiHuanQianBeiPresent();
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
