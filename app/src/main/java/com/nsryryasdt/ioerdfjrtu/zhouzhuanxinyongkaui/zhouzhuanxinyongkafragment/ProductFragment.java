package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkafragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaadapter.GoodsItemAdapterZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.GoodsZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.mvp.XFragment;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent.ZhouZhuanXinYongKaProductPresent;
import com.nsryryasdt.ioerdfjrtu.router.Router;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.WebViewActivityZhouZhuanXinYongKa;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductFragment extends XFragment<ZhouZhuanXinYongKaProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterZhouZhuanXinYongKa goodsItemAdapterZhouZhuanXinYongKa;
    private GoodsZhouZhuanXinYongKaModel goodsZhouZhuanXinYongKaModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        parentLl.setOnClickListener(v -> {
            productClick(goodsZhouZhuanXinYongKaModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhou_zhuan_xin_yong_ka_product;
    }

    @Override
    public ZhouZhuanXinYongKaProductPresent newP() {
        return new ZhouZhuanXinYongKaProductPresent();
    }

    private void productClick(GoodsZhouZhuanXinYongKaModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsZhouZhuanXinYongKaModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityZhouZhuanXinYongKa.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsZhouZhuanXinYongKaModel> mData) {
        if (goodsItemAdapterZhouZhuanXinYongKa == null) {
            goodsItemAdapterZhouZhuanXinYongKa = new GoodsItemAdapterZhouZhuanXinYongKa(getActivity());
            goodsItemAdapterZhouZhuanXinYongKa.setRecItemClick(new RecyclerItemCallback<GoodsZhouZhuanXinYongKaModel, GoodsItemAdapterZhouZhuanXinYongKa.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsZhouZhuanXinYongKaModel model, int tag, GoodsItemAdapterZhouZhuanXinYongKa.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterZhouZhuanXinYongKa.setHasStableIds(true);
            goodsItemAdapterZhouZhuanXinYongKa.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterZhouZhuanXinYongKa);
        } else {
            goodsItemAdapterZhouZhuanXinYongKa.setData(mData);
        }
    }

    public void setModel(GoodsZhouZhuanXinYongKaModel goodsZhouZhuanXinYongKaModel) {
        this.goodsZhouZhuanXinYongKaModel = goodsZhouZhuanXinYongKaModel;
    }
}
