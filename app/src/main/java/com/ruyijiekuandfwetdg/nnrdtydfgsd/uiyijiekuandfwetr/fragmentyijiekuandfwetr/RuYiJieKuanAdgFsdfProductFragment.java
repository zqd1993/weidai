package com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.fragmentyijiekuandfwetr;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.adapterruyijiekuandfwetr.GoodsItemAdapter1RuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfGoodsModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XFragment;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.presentyijiekuandfwetr.RuYiJieKuanAdgFsdfProductPresent;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.router.Router;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.WebViewActivityRuYiJieKuanAdgFsdf;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class RuYiJieKuanAdgFsdfProductFragment extends XFragment<RuYiJieKuanAdgFsdfProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapter1RuYiJieKuanAdgFsdf goodsItemAdapterJieKuanZhiJia;
    private RuYiJieKuanAdgFsdfGoodsModel ruYiJieKuanAdgFsdfGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public RuYiJieKuanAdgFsdfProductPresent newP() {
        return new RuYiJieKuanAdgFsdfProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
        if (goodsItemAdapterJieKuanZhiJia == null) {
            goodsItemAdapterJieKuanZhiJia = new GoodsItemAdapter1RuYiJieKuanAdgFsdf(getActivity());
            goodsItemAdapterJieKuanZhiJia.setRecItemClick(new RecyclerItemCallback<RuYiJieKuanAdgFsdfGoodsModel, GoodsItemAdapter1RuYiJieKuanAdgFsdf.ViewHolder>() {
                @Override
                public void onItemClick(int position, RuYiJieKuanAdgFsdfGoodsModel model, int tag, GoodsItemAdapter1RuYiJieKuanAdgFsdf.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterJieKuanZhiJia.setHasStableIds(true);
            goodsItemAdapterJieKuanZhiJia.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterJieKuanZhiJia);
        } else {
            goodsItemAdapterJieKuanZhiJia.setData(mData);
        }
    }

    public void setModel(RuYiJieKuanAdgFsdfGoodsModel ruYiJieKuanAdgFsdfGoodsModel) {
        this.ruYiJieKuanAdgFsdfGoodsModel = ruYiJieKuanAdgFsdfGoodsModel;
    }
}
