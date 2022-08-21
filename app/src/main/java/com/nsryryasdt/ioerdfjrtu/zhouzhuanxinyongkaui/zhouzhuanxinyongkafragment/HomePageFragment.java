package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkafragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaadapter.GoodsItemAdapterZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.GoodsZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.WebViewActivityZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.mvp.XFragment;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent.HomePagePresentZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragment extends XFragment<HomePagePresentZhouZhuanXinYongKa> {

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
    @BindView(R.id.top_img)
    public ImageView topImg;

    private Bundle webBundle;
    public GoodsItemAdapterZhouZhuanXinYongKa goodsItemAdapterZhouZhuanXinYongKa;
    public GoodsZhouZhuanXinYongKaModel goodsZhouZhuanXinYongKaModel, topGoodsZhouZhuanXinYongKaModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().aindex();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        topImg.setOnClickListener(v -> {
            productClick(topGoodsZhouZhuanXinYongKaModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_zhou_zhuan_xin_yong_ka;
    }

    @Override
    public HomePagePresentZhouZhuanXinYongKa newP() {
        return new HomePagePresentZhouZhuanXinYongKa();
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
