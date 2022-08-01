package com.jinyu.xiaopu.fenfujieui.fenfujiefragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.fenfujieadapter.GoodsItemAdapterFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.GoodsFenFuJieModel;
import com.jinyu.xiaopu.mvp.XActivity;
import com.jinyu.xiaopu.fenfujieui.WebViewActivityFenFuJie;
import com.jinyu.xiaopu.mvp.XFragment;
import com.jinyu.xiaopu.fenfujiepresent.HomePagePresentFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentFenFuJie extends XFragment<HomePagePresentFenFuJie> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    public RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_fl)
    public View noDataFl;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.bg_img)
    View bg_img;
    @BindView(R.id.tuijianchanpin_ll)
    View tuijianchanpin_ll;
    @BindView(R.id.asdas_view)
    View asdas_view;
    @BindView(R.id.root_fl)
    View root_fl;

    private Bundle bundle, webBundle;
    private int tag;
    public GoodsItemAdapterFenFuJie goodsItemAdapterFenFuJie;
    public GoodsFenFuJieModel goodsFenFuJieModel;

    public static HomePageFragmentFenFuJie getInstant(int tag) {
        HomePageFragmentFenFuJie homePageFragmentFenFuJie = new HomePageFragmentFenFuJie();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragmentFenFuJie.setArguments(bundle);
        return homePageFragmentFenFuJie;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt("tag");
            if (tag == 1) {
                productBg.setVisibility(View.GONE);
                homePageBg.setVisibility(View.VISIBLE);
                bg_img.setVisibility(View.VISIBLE);
//                tuijianchanpin_ll.setVisibility(View.VISIBLE);
                asdas_view.setVisibility(View.VISIBLE);
            } else {
                productBg.setVisibility(View.VISIBLE);
                homePageBg.setVisibility(View.GONE);
                bg_img.setVisibility(View.GONE);
//                tuijianchanpin_ll.setVisibility(View.GONE);
                asdas_view.setVisibility(View.GONE);
            }
        }
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
        root_fl.setOnClickListener(v -> {
            productClick(goodsFenFuJieModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(goodsFenFuJieModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(goodsFenFuJieModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_fu_jie_home_page;
    }

    @Override
    public HomePagePresentFenFuJie newP() {
        return new HomePagePresentFenFuJie();
    }

    private void productClick(GoodsFenFuJieModel model) {
        if (model != null) {
            getP().productClick(model);
        }
    }

    public void jumpWebActivity(GoodsFenFuJieModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            StaticFenFuJieUtil.getValue((XActivity) getActivity(), WebViewActivityFenFuJie.class, webBundle);
        }
    }

    public void initGoodsItemAdapter(List<GoodsFenFuJieModel> mData) {
        if (goodsItemAdapterFenFuJie == null) {
            goodsItemAdapterFenFuJie = new GoodsItemAdapterFenFuJie(getActivity());
            goodsItemAdapterFenFuJie.setRecItemClick(new RecyclerItemCallback<GoodsFenFuJieModel, GoodsItemAdapterFenFuJie.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsFenFuJieModel model, int tag, GoodsItemAdapterFenFuJie.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterFenFuJie.setHasStableIds(true);
            goodsItemAdapterFenFuJie.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterFenFuJie);
        } else {
            goodsItemAdapterFenFuJie.setData(mData);
        }
    }

    public void setModel(GoodsFenFuJieModel goodsFenFuJieModel) {
        this.goodsFenFuJieModel = goodsFenFuJieModel;
    }
}
