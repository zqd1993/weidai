package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.GoodsItemAdapterFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.GoodsFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.HomePageFenQiHuanQianBeiActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.WebViewActivityFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.mvp.XFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent.FenQiHuanQianBeiHomePagePresent;
import com.mmaeryrusu.qqzdryty.router.Router;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class FenQiHuanQianBeiHomePageFragment extends XFragment<FenQiHuanQianBeiHomePagePresent> {

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
    @BindView(R.id.edu_tv)
    public TextView edu_tv;
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.renshu_tv)
    public TextView renshu_tv;
    @BindView(R.id.month_tv)
    public TextView month_tv;
    @BindView(R.id.mianxi_tv)
    public TextView mianxi_tv;
    @BindView(R.id.lingqu_sl)
    View lingqu_sl;
    @BindView(R.id.more_ll)
    View more_ll;

    private Bundle webBundle;
    public GoodsItemAdapterFenQiHuanQianBei goodsItemAdapterFenQiHuanQianBei;
    public GoodsFenQiHuanQianBeiModel goodsFenQiHuanQianBeiModel, topGoodsFenQiHuanQianBeiModel;
    private SimpleDateFormat sdf;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        lingqu_sl.setOnClickListener(v -> {
            productClick(topGoodsFenQiHuanQianBeiModel);
        });
        more_ll.setOnClickListener(v -> {
            ((HomePageFenQiHuanQianBeiActivity)getActivity()).changePage();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_fen_qi_huan_qian_bei;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
//        date_tv.setText(sdf.format(System.currentTimeMillis()));
    }

    @Override
    public FenQiHuanQianBeiHomePagePresent newP() {
        return new FenQiHuanQianBeiHomePagePresent();
    }

    private void productClick(GoodsFenQiHuanQianBeiModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsFenQiHuanQianBeiModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityFenQiHuanQianBei.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsFenQiHuanQianBeiModel> mData) {
        if (goodsItemAdapterFenQiHuanQianBei == null) {
            goodsItemAdapterFenQiHuanQianBei = new GoodsItemAdapterFenQiHuanQianBei(getActivity());
            goodsItemAdapterFenQiHuanQianBei.setRecItemClick(new RecyclerItemCallback<GoodsFenQiHuanQianBeiModel, GoodsItemAdapterFenQiHuanQianBei.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsFenQiHuanQianBeiModel model, int tag, GoodsItemAdapterFenQiHuanQianBei.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterFenQiHuanQianBei.setHasStableIds(true);
            goodsItemAdapterFenQiHuanQianBei.setData(mData);
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterFenQiHuanQianBei);
        } else {
            goodsItemAdapterFenQiHuanQianBei.setData(mData);
        }
    }

    public void setModel(GoodsFenQiHuanQianBeiModel goodsFenQiHuanQianBeiModel) {
        this.goodsFenQiHuanQianBeiModel = goodsFenQiHuanQianBeiModel;
    }
}
