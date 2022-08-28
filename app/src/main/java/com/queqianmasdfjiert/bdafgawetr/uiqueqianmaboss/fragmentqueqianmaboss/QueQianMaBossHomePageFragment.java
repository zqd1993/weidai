package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.fragmentqueqianmaboss;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.adapterqueqianmaboss.GoodsItemQueQianMaBossAdapter;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.GoodsQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.QueQianMaBossWebViewActivity;
import com.queqianmasdfjiert.bdafgawetr.mvp.XFragment;
import com.queqianmasdfjiert.bdafgawetr.presentqueqianmaboss.HomePagePresentQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class QueQianMaBossHomePageFragment extends XFragment<HomePagePresentQueQianMaBoss> {

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
    public GoodsItemQueQianMaBossAdapter goodsItemQueQianMaBossAdapter;
    public GoodsQueQianMaBossModel goodsQueQianMaBossModel, topGoodsQueQianMaBossModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsQueQianMaBossModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_que_qian_ma_boss;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public HomePagePresentQueQianMaBoss newP() {
        return new HomePagePresentQueQianMaBoss();
    }

    private void productClick(GoodsQueQianMaBossModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsQueQianMaBossModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(QueQianMaBossWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsQueQianMaBossModel> mData) {
        if (goodsItemQueQianMaBossAdapter == null) {
            goodsItemQueQianMaBossAdapter = new GoodsItemQueQianMaBossAdapter(getActivity());
            goodsItemQueQianMaBossAdapter.setRecItemClick(new RecyclerItemCallback<GoodsQueQianMaBossModel, GoodsItemQueQianMaBossAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsQueQianMaBossModel model, int tag, GoodsItemQueQianMaBossAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemQueQianMaBossAdapter.setHasStableIds(true);
            goodsItemQueQianMaBossAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemQueQianMaBossAdapter);
        } else {
            goodsItemQueQianMaBossAdapter.setData(mData);
        }
    }

    public void setModel(GoodsQueQianMaBossModel goodsQueQianMaBossModel) {
        this.goodsQueQianMaBossModel = goodsQueQianMaBossModel;
    }
}
