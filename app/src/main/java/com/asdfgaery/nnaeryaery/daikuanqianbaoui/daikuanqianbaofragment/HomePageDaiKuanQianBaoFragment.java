package com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaofragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaodapter.GoodsItemAdapter1DaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.GoodsModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.WebViewActivityDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.mvp.XFragment;
import com.asdfgaery.nnaeryaery.daikuanqianbaopresent.HomePageDaiKuanQianBaoPresent;
import com.asdfgaery.nnaeryaery.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageDaiKuanQianBaoFragment extends XFragment<HomePageDaiKuanQianBaoPresent> {

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

    private Bundle webBundle;
    public GoodsItemAdapter1DaiKuanQianBao goodsItemAdapterDiXiDaiKuan;
    public GoodsModelDaiKuanQianBao goodsModelDaiKuanQianBao, topGoodsModelDaiKuanQianBao;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topGoodsModelDaiKuanQianBao);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_dai_kuan_qian_bao_page;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public HomePageDaiKuanQianBaoPresent newP() {
        return new HomePageDaiKuanQianBaoPresent();
    }

    private void productClick(GoodsModelDaiKuanQianBao model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModelDaiKuanQianBao model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityDaiKuanQianBao.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModelDaiKuanQianBao> mData) {
        if (goodsItemAdapterDiXiDaiKuan == null) {
            goodsItemAdapterDiXiDaiKuan = new GoodsItemAdapter1DaiKuanQianBao(getActivity());
            goodsItemAdapterDiXiDaiKuan.setRecItemClick(new RecyclerItemCallback<GoodsModelDaiKuanQianBao, GoodsItemAdapter1DaiKuanQianBao.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelDaiKuanQianBao model, int tag, GoodsItemAdapter1DaiKuanQianBao.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterDiXiDaiKuan.setHasStableIds(true);
            goodsItemAdapterDiXiDaiKuan.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterDiXiDaiKuan);
        } else {
            goodsItemAdapterDiXiDaiKuan.setData(mData);
        }
    }

    public void setModel(GoodsModelDaiKuanQianBao goodsModelDaiKuanQianBao) {
        this.goodsModelDaiKuanQianBao = goodsModelDaiKuanQianBao;
    }
}
