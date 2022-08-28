package com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.jiejijietiaofragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoadapter.GoodsItemAdapterJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.GoodsModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XFragment;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaopresent.JiuJiJieTiaojghsdfProductPresent;
import com.jiujijietiaodsfwet.bsdwefhert.router.Router;
import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.WebViewActivityJiuJiJieTiaojghsdf;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductJiuJiJieTiaojghsdfFragment extends XFragment<JiuJiJieTiaojghsdfProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterJiuJiJieTiaojghsdf goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan;
    private GoodsModelJiuJiJieTiaojghsdf goodsModelJiuJiJieTiaojghsdf;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModelJiuJiJieTiaojghsdf);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jiu_ji_jie_tiao_boss_product;
    }

    @Override
    public JiuJiJieTiaojghsdfProductPresent newP() {
        return new JiuJiJieTiaojghsdfProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    private void productClick(GoodsModelJiuJiJieTiaojghsdf model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModelJiuJiJieTiaojghsdf model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityJiuJiJieTiaojghsdf.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModelJiuJiJieTiaojghsdf> mData) {
        if (goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan == null) {
            goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan = new GoodsItemAdapterJiuJiJieTiaojghsdf(getActivity());
            goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan.setRecItemClick(new RecyclerItemCallback<GoodsModelJiuJiJieTiaojghsdf, GoodsItemAdapterJiuJiJieTiaojghsdf.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelJiuJiJieTiaojghsdf model, int tag, GoodsItemAdapterJiuJiJieTiaojghsdf.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan.setHasStableIds(true);
            goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan);
        } else {
            goodsItemAdapterJiuJiJieTiaojghsdfDiXiDaiKuan.setData(mData);
        }
    }

    public void setModel(GoodsModelJiuJiJieTiaojghsdf goodsModelJiuJiJieTiaojghsdf) {
        this.goodsModelJiuJiJieTiaojghsdf = goodsModelJiuJiJieTiaojghsdf;
    }
}
