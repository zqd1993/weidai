package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiadapter.GoodsItemAdapterKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.GoodsModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.KuaiDianFenQiDaiWebViewActivity;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaipresent.HomePagePresentKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentKuaiDianFenQiDai extends XFragment<HomePagePresentKuaiDianFenQiDai> {

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
    public GoodsItemAdapterKuaiDianFenQiDai goodsItemAdapterKuaiDianFenQiDai;
    public GoodsModelKuaiDianFenQiDai goodsModelKuaiDianFenQiDai, topGoodsModelKuaiDianFenQiDai;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsModelKuaiDianFenQiDai);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_kuai_dian_fen_qi_dai;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public HomePagePresentKuaiDianFenQiDai newP() {
        return new HomePagePresentKuaiDianFenQiDai();
    }

    private void productClick(GoodsModelKuaiDianFenQiDai model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModelKuaiDianFenQiDai model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(KuaiDianFenQiDaiWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModelKuaiDianFenQiDai> mData) {
        if (goodsItemAdapterKuaiDianFenQiDai == null) {
            goodsItemAdapterKuaiDianFenQiDai = new GoodsItemAdapterKuaiDianFenQiDai(getActivity());
            goodsItemAdapterKuaiDianFenQiDai.setRecItemClick(new RecyclerItemCallback<GoodsModelKuaiDianFenQiDai, GoodsItemAdapterKuaiDianFenQiDai.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelKuaiDianFenQiDai model, int tag, GoodsItemAdapterKuaiDianFenQiDai.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterKuaiDianFenQiDai.setHasStableIds(true);
            goodsItemAdapterKuaiDianFenQiDai.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterKuaiDianFenQiDai);
        } else {
            goodsItemAdapterKuaiDianFenQiDai.setData(mData);
        }
    }

    public void setModel(GoodsModelKuaiDianFenQiDai goodsModelKuaiDianFenQiDai) {
        this.goodsModelKuaiDianFenQiDai = goodsModelKuaiDianFenQiDai;
    }
}
