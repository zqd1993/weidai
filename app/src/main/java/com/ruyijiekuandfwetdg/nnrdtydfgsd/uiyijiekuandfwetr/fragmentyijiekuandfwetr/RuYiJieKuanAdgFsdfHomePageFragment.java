package com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.fragmentyijiekuandfwetr;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.adapterruyijiekuandfwetr.GoodsItemAdapterRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfGoodsModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.WebViewActivityRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XFragment;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.presentyijiekuandfwetr.RuYiJieKuanAdgFsdfHomePagePresent;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class RuYiJieKuanAdgFsdfHomePageFragment extends XFragment<RuYiJieKuanAdgFsdfHomePagePresent> {

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
    @BindView(R.id.info_tv)
    public TextView info_tv;

    private Bundle webBundle;
    public GoodsItemAdapterRuYiJieKuanAdgFsdf goodsItemAdapterRuYiJieKuanAdgFsdf;
    public RuYiJieKuanAdgFsdfGoodsModel ruYiJieKuanAdgFsdfGoodsModel, topRuYiJieKuanAdgFsdfGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topRuYiJieKuanAdgFsdfGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public RuYiJieKuanAdgFsdfHomePagePresent newP() {
        return new RuYiJieKuanAdgFsdfHomePagePresent();
    }

    private void productClick(RuYiJieKuanAdgFsdfGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(RuYiJieKuanAdgFsdfGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityRuYiJieKuanAdgFsdf.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<RuYiJieKuanAdgFsdfGoodsModel> mData) {
        if (goodsItemAdapterRuYiJieKuanAdgFsdf == null) {
            goodsItemAdapterRuYiJieKuanAdgFsdf = new GoodsItemAdapterRuYiJieKuanAdgFsdf(getActivity());
            goodsItemAdapterRuYiJieKuanAdgFsdf.setRecItemClick(new RecyclerItemCallback<RuYiJieKuanAdgFsdfGoodsModel, GoodsItemAdapterRuYiJieKuanAdgFsdf.ViewHolder>() {
                @Override
                public void onItemClick(int position, RuYiJieKuanAdgFsdfGoodsModel model, int tag, GoodsItemAdapterRuYiJieKuanAdgFsdf.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterRuYiJieKuanAdgFsdf.setHasStableIds(true);
            goodsItemAdapterRuYiJieKuanAdgFsdf.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterRuYiJieKuanAdgFsdf);
        } else {
            goodsItemAdapterRuYiJieKuanAdgFsdf.setData(mData);
        }
    }

    public void setModel(RuYiJieKuanAdgFsdfGoodsModel ruYiJieKuanAdgFsdfGoodsModel) {
        this.ruYiJieKuanAdgFsdfGoodsModel = ruYiJieKuanAdgFsdfGoodsModel;
    }
}
