package com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaofragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.model.GoodsModel;
import com.queqianmasdfjiert.bdafgawetr.ui.WebViewActivity;
import com.queqianmasdfjiert.bdafgawetr.adapter.GoodsItemAdapter1;
import com.queqianmasdfjiert.bdafgawetr.mvp.XFragment;
import com.queqianmasdfjiert.bdafgawetr.present.HomePagePresent;
import com.queqianmasdfjiert.bdafgawetr.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragment extends XFragment<HomePagePresent> {

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
    public GoodsItemAdapter1 goodsItemAdapter1;
    public GoodsModel goodsModel, topGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
        if (goodsItemAdapter1 == null) {
            goodsItemAdapter1 = new GoodsItemAdapter1(getActivity());
            goodsItemAdapter1.setRecItemClick(new RecyclerItemCallback<GoodsModel, GoodsItemAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModel model, int tag, GoodsItemAdapter1.ViewHolder holder) {
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

    public void setModel(GoodsModel goodsModel) {
        this.goodsModel = goodsModel;
    }
}
