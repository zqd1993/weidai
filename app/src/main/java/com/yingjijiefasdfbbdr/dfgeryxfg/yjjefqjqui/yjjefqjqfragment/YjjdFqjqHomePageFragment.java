package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqadapter.GoodsItemYjjdFqjqAdapter1;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.GoodsModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.YjjdFqjqWebViewActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XFragment;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqpresent.HomePagePresentYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class YjjdFqjqHomePageFragment extends XFragment<HomePagePresentYjjdFqjq> {

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
    public GoodsItemYjjdFqjqAdapter1 goodsItemYjjdFqjqAdapter1;
    public GoodsModelYjjdFqjq goodsModelYjjdFqjq, topGoodsModelYjjdFqjq;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsModelYjjdFqjq);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_yjjdfqjq;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public HomePagePresentYjjdFqjq newP() {
        return new HomePagePresentYjjdFqjq();
    }

    private void productClick(GoodsModelYjjdFqjq model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModelYjjdFqjq model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(YjjdFqjqWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModelYjjdFqjq> mData) {
        if (goodsItemYjjdFqjqAdapter1 == null) {
            goodsItemYjjdFqjqAdapter1 = new GoodsItemYjjdFqjqAdapter1(getActivity());
            goodsItemYjjdFqjqAdapter1.setRecItemClick(new RecyclerItemCallback<GoodsModelYjjdFqjq, GoodsItemYjjdFqjqAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelYjjdFqjq model, int tag, GoodsItemYjjdFqjqAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemYjjdFqjqAdapter1.setHasStableIds(true);
            goodsItemYjjdFqjqAdapter1.setData(mData);
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemYjjdFqjqAdapter1);
        } else {
            goodsItemYjjdFqjqAdapter1.setData(mData);
        }
    }

    public void setModel(GoodsModelYjjdFqjq goodsModelYjjdFqjq) {
        this.goodsModelYjjdFqjq = goodsModelYjjdFqjq;
    }
}
