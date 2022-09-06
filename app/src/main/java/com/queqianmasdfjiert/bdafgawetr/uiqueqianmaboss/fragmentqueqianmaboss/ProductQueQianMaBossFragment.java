package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.fragmentqueqianmaboss;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.adapterqueqianmaboss.GoodsItemAdapterQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.GoodsQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.mvp.XFragment;
import com.queqianmasdfjiert.bdafgawetr.presentqueqianmaboss.ProductPresentQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.router.Router;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.QueQianMaBossWebViewActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductQueQianMaBossFragment extends XFragment<ProductPresentQueQianMaBoss> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterQueQianMaBoss goodsItemAdapterQueQianMaBossDiXiDaiKuan;
    private GoodsQueQianMaBossModel goodsQueQianMaBossModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsQueQianMaBossModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_que_qian_ma_boss;
    }

    @Override
    public ProductPresentQueQianMaBoss newP() {
        return new ProductPresentQueQianMaBoss();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
        if (goodsItemAdapterQueQianMaBossDiXiDaiKuan == null) {
            goodsItemAdapterQueQianMaBossDiXiDaiKuan = new GoodsItemAdapterQueQianMaBoss(getActivity());
            goodsItemAdapterQueQianMaBossDiXiDaiKuan.setRecItemClick(new RecyclerItemCallback<GoodsQueQianMaBossModel, GoodsItemAdapterQueQianMaBoss.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsQueQianMaBossModel model, int tag, GoodsItemAdapterQueQianMaBoss.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterQueQianMaBossDiXiDaiKuan.setHasStableIds(true);
            goodsItemAdapterQueQianMaBossDiXiDaiKuan.setData(mData);
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterQueQianMaBossDiXiDaiKuan);
        } else {
            goodsItemAdapterQueQianMaBossDiXiDaiKuan.setData(mData);
        }
    }

    public void setModel(GoodsQueQianMaBossModel goodsQueQianMaBossModel) {
        this.goodsQueQianMaBossModel = goodsQueQianMaBossModel;
    }
}
