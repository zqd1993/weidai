package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkafragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaadapter.SuiJieXinYongKaGoodsItemAdapter;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.GoodsSuiJieXinYongKaModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.WebViewSuiJieXinYongKaActivity;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XFragment;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkapresent.HomePagePresentSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentSuiJieXinYongKa extends XFragment<HomePagePresentSuiJieXinYongKa> {

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
    @BindView(R.id.click_view)
    View click_view;
    @BindView(R.id.top_img)
    public ImageView topImg;

    private Bundle webBundle;
    public SuiJieXinYongKaGoodsItemAdapter suiJieXinYongKaGoodsItemAdapter;
    public GoodsSuiJieXinYongKaModel goodsSuiJieXinYongKaModel, topGoodsSuiJieXinYongKaModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().aindex();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        topImg.setOnClickListener(v -> {
            productClick(topGoodsSuiJieXinYongKaModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_sui_jie_xin_yong_ka;
    }

    @Override
    public HomePagePresentSuiJieXinYongKa newP() {
        return new HomePagePresentSuiJieXinYongKa();
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
