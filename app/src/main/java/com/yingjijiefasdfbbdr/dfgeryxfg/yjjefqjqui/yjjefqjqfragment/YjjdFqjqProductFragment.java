package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqfragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqadapter.GoodsItemYjjdFqjqAdapter;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.GoodsModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XFragment;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqpresent.YjjdFqjqProductPresent;
import com.yingjijiefasdfbbdr.dfgeryxfg.router.Router;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.YjjdFqjqWebViewActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class YjjdFqjqProductFragment extends XFragment<YjjdFqjqProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemYjjdFqjqAdapter goodsItemYjjdFqjqAdapterDiXiDaiKuan;
    private GoodsModelYjjdFqjq goodsModelYjjdFqjq;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModelYjjdFqjq);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_yjjdfqjq_product;
    }

    @Override
    public YjjdFqjqProductPresent newP() {
        return new YjjdFqjqProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
        if (goodsItemYjjdFqjqAdapterDiXiDaiKuan == null) {
            goodsItemYjjdFqjqAdapterDiXiDaiKuan = new GoodsItemYjjdFqjqAdapter(getActivity());
            goodsItemYjjdFqjqAdapterDiXiDaiKuan.setRecItemClick(new RecyclerItemCallback<GoodsModelYjjdFqjq, GoodsItemYjjdFqjqAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelYjjdFqjq model, int tag, GoodsItemYjjdFqjqAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemYjjdFqjqAdapterDiXiDaiKuan.setHasStableIds(true);
            goodsItemYjjdFqjqAdapterDiXiDaiKuan.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemYjjdFqjqAdapterDiXiDaiKuan);
        } else {
            goodsItemYjjdFqjqAdapterDiXiDaiKuan.setData(mData);
        }
    }

    public void setModel(GoodsModelYjjdFqjq goodsModelYjjdFqjq) {
        this.goodsModelYjjdFqjq = goodsModelYjjdFqjq;
    }
}
