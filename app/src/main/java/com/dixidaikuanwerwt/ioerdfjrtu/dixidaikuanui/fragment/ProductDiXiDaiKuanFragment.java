package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanadapter.GoodsItemAdapterDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.GoodsModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XFragment;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanpresent.DiXiDaiKuanProductPresent;
import com.dixidaikuanwerwt.ioerdfjrtu.router.Router;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.WebViewActivityDiXiDaiKuan;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductDiXiDaiKuanFragment extends XFragment<DiXiDaiKuanProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterDiXiDaiKuan goodsItemAdapterDiXiDaiKuan;
    private GoodsModelDiXiDaiKuan goodsModelDiXiDaiKuan;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModelDiXiDaiKuan);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_di_xi_dai_kuan_product;
    }

    @Override
    public DiXiDaiKuanProductPresent newP() {
        return new DiXiDaiKuanProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    private void productClick(GoodsModelDiXiDaiKuan model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsModelDiXiDaiKuan model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityDiXiDaiKuan.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsModelDiXiDaiKuan> mData) {
        if (goodsItemAdapterDiXiDaiKuan == null) {
            goodsItemAdapterDiXiDaiKuan = new GoodsItemAdapterDiXiDaiKuan(getActivity());
            goodsItemAdapterDiXiDaiKuan.setRecItemClick(new RecyclerItemCallback<GoodsModelDiXiDaiKuan, GoodsItemAdapterDiXiDaiKuan.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelDiXiDaiKuan model, int tag, GoodsItemAdapterDiXiDaiKuan.ViewHolder holder) {
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

    public void setModel(GoodsModelDiXiDaiKuan goodsModelDiXiDaiKuan) {
        this.goodsModelDiXiDaiKuan = goodsModelDiXiDaiKuan;
    }
}
