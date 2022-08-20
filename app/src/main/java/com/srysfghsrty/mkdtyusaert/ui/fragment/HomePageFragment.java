package com.srysfghsrty.mkdtyusaert.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.adapter.GoodsItemAdapter;
import com.srysfghsrty.mkdtyusaert.model.GoodsModel;
import com.srysfghsrty.mkdtyusaert.ui.HomePageActivity;
import com.srysfghsrty.mkdtyusaert.ui.WebViewActivity;
import com.srysfghsrty.mkdtyusaert.mvp.XFragment;
import com.srysfghsrty.mkdtyusaert.present.HomePagePresent;
import com.srysfghsrty.mkdtyusaert.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragment extends XFragment<HomePagePresent> {

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
    @BindView(R.id.top_img)
    public ImageView topImg;
    @BindView(R.id.max_money_tv)
    public TextView max_money_tv;
    @BindView(R.id.shenqing_fl)
    View shenqing_fl;

    private Bundle webBundle;
    public GoodsItemAdapter goodsItemAdapter;
    public GoodsModel goodsModel, topGoodsModel;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        topImg.setOnClickListener(v -> {
            productClick(topGoodsModel);
        });
        shenqing_fl.setOnClickListener(v -> {
            productClick(topGoodsModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public HomePagePresent newP() {
        return new HomePagePresent();
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
