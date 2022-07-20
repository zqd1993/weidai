package com.suishijie.ndjufgjhdrty.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.suishijie.ndjufgjhdrty.R;
import com.suishijie.ndjufgjhdrty.adapter.GoodsItemAdapter;
import com.suishijie.ndjufgjhdrty.model.GoodsModel;
import com.suishijie.ndjufgjhdrty.ui.WebViewActivity;
import com.suishijie.ndjufgjhdrty.mvp.XFragment;
import com.suishijie.ndjufgjhdrty.present.HomePagePresent;
import com.suishijie.ndjufgjhdrty.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragment extends XFragment<HomePagePresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
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
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.top_img)
    public ImageView topImg;

    private Bundle webBundle;
    public GoodsItemAdapter goodsItemAdapter;
    public GoodsModel goodsModel, topGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
//        getP().productList();
//        ToastUtil.showShort("API_BASE_URL = " + SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + Api.API_BASE_URL);
        getP().aindex();
        swipeRefreshLayout.setOnRefreshListener(() -> {
//            getP().productList();
            getP().aindex();
        });
//        productBg.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        swipeRefreshLayout.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        topLayout.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        click_view.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        click_view_1.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
        topImg.setOnClickListener(v -> {
            productClick(topGoodsModel);
        });
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
