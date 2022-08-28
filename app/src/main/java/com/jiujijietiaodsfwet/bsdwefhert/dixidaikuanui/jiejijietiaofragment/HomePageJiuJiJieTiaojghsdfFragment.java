package com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.jiejijietiaofragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.WebViewActivityJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoadapter.GoodsItemJiuJiJieTiaojghsdfAdapter;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.GoodsModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XFragment;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaopresent.HomePageJiuJiJieTiaojghsdfPresent;
import com.jiujijietiaodsfwet.bsdwefhert.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageJiuJiJieTiaojghsdfFragment extends XFragment<HomePageJiuJiJieTiaojghsdfPresent> {

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
    public GoodsItemJiuJiJieTiaojghsdfAdapter goodsItemJiuJiJieTiaojghsdfAdapter;
    public GoodsModelJiuJiJieTiaojghsdf goodsModelJiuJiJieTiaojghsdf, topGoodsModelJiuJiJieTiaojghsdf;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsModelJiuJiJieTiaojghsdf);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_jiu_ji_jie_tiao_boss_page;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public HomePageJiuJiJieTiaojghsdfPresent newP() {
        return new HomePageJiuJiJieTiaojghsdfPresent();
    }

    private void productClick(GoodsModelJiuJiJieTiaojghsdf model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModelJiuJiJieTiaojghsdf model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityJiuJiJieTiaojghsdf.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModelJiuJiJieTiaojghsdf> mData) {
        if (goodsItemJiuJiJieTiaojghsdfAdapter == null) {
            goodsItemJiuJiJieTiaojghsdfAdapter = new GoodsItemJiuJiJieTiaojghsdfAdapter(getActivity());
            goodsItemJiuJiJieTiaojghsdfAdapter.setRecItemClick(new RecyclerItemCallback<GoodsModelJiuJiJieTiaojghsdf, GoodsItemJiuJiJieTiaojghsdfAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelJiuJiJieTiaojghsdf model, int tag, GoodsItemJiuJiJieTiaojghsdfAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemJiuJiJieTiaojghsdfAdapter.setHasStableIds(true);
            goodsItemJiuJiJieTiaojghsdfAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemJiuJiJieTiaojghsdfAdapter);
        } else {
            goodsItemJiuJiJieTiaojghsdfAdapter.setData(mData);
        }
    }

    public void setModel(GoodsModelJiuJiJieTiaojghsdf goodsModelJiuJiJieTiaojghsdf) {
        this.goodsModelJiuJiJieTiaojghsdf = goodsModelJiuJiJieTiaojghsdf;
    }
}
