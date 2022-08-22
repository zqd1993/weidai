package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkafragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaadapter.SuiJieXinYongKaGoodsItemAdapter;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.GoodsSuiJieXinYongKaModel;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XFragment;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkapresent.ProductSuiJieXinYongKaPresent;
import com.suijiexinyongkafwert.dffdgaeryt.router.Router;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.WebViewSuiJieXinYongKaActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductSuiJieXinYongKaFragment extends XFragment<ProductSuiJieXinYongKaPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public SuiJieXinYongKaGoodsItemAdapter suiJieXinYongKaGoodsItemAdapter;
    private GoodsSuiJieXinYongKaModel goodsSuiJieXinYongKaModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        parentLl.setOnClickListener(v -> {
            productClick(goodsSuiJieXinYongKaModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_sui_jie_xin_yong_ka;
    }

    @Override
    public ProductSuiJieXinYongKaPresent newP() {
        return new ProductSuiJieXinYongKaPresent();
    }

    private void productClick(GoodsSuiJieXinYongKaModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsSuiJieXinYongKaModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewSuiJieXinYongKaActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsSuiJieXinYongKaModel> mData) {
        if (suiJieXinYongKaGoodsItemAdapter == null) {
            suiJieXinYongKaGoodsItemAdapter = new SuiJieXinYongKaGoodsItemAdapter(getActivity());
            suiJieXinYongKaGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<GoodsSuiJieXinYongKaModel, SuiJieXinYongKaGoodsItemAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsSuiJieXinYongKaModel model, int tag, SuiJieXinYongKaGoodsItemAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            suiJieXinYongKaGoodsItemAdapter.setHasStableIds(true);
            suiJieXinYongKaGoodsItemAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(suiJieXinYongKaGoodsItemAdapter);
        } else {
            suiJieXinYongKaGoodsItemAdapter.setData(mData);
        }
    }

    public void setModel(GoodsSuiJieXinYongKaModel goodsSuiJieXinYongKaModel) {
        this.goodsSuiJieXinYongKaModel = goodsSuiJieXinYongKaModel;
    }
}
