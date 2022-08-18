package com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaofragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaodapter.GoodsItemAdapterDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.GoodsModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.mvp.XFragment;
import com.asdfgaery.nnaeryaery.daikuanqianbaopresent.DaiKuanQianBaoProductPresent;
import com.asdfgaery.nnaeryaery.router.Router;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.WebViewActivityDaiKuanQianBao;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductDaiKuanQianBaoFragment extends XFragment<DaiKuanQianBaoProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterDaiKuanQianBao goodsItemAdapterDaiKuanQianBao;
    private GoodsModelDaiKuanQianBao goodsModelDaiKuanQianBao;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModelDiXiDaiKuan);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dai_kuan_qian_bao_product;
    }

    @Override
    public DaiKuanQianBaoProductPresent newP() {
        return new DaiKuanQianBaoProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
        if (goodsItemAdapterDaiKuanQianBao == null) {
            goodsItemAdapterDaiKuanQianBao = new GoodsItemAdapterDaiKuanQianBao(getActivity());
            goodsItemAdapterDaiKuanQianBao.setRecItemClick(new RecyclerItemCallback<GoodsModelDaiKuanQianBao, GoodsItemAdapterDaiKuanQianBao.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModelDaiKuanQianBao model, int tag, GoodsItemAdapterDaiKuanQianBao.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterDaiKuanQianBao.setHasStableIds(true);
            goodsItemAdapterDaiKuanQianBao.setData(mData);
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterDaiKuanQianBao);
        } else {
            goodsItemAdapterDaiKuanQianBao.setData(mData);
        }
    }

    public void setModel(GoodsModelDaiKuanQianBao goodsModelDaiKuanQianBao) {
        this.goodsModelDaiKuanQianBao = goodsModelDaiKuanQianBao;
    }
}
