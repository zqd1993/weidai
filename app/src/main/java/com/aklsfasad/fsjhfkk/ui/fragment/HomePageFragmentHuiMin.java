package com.aklsfasad.fsjhfkk.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.adapter.GoodsItemHuiMinAdapter;
import com.aklsfasad.fsjhfkk.adapter.GoodsItemHuiMinAdapter1;
import com.aklsfasad.fsjhfkk.adapter.ImageAdapter;
import com.aklsfasad.fsjhfkk.model.GoodsHuiMinModel;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.ui.LoginActivityHuiMin;
import com.aklsfasad.fsjhfkk.ui.TanPingActivity;
import com.aklsfasad.fsjhfkk.ui.WebHuiMinActivity;
import com.aklsfasad.fsjhfkk.mvp.XFragment;
import com.aklsfasad.fsjhfkk.present.HomePagePresentHuiMin;
import com.aklsfasad.fsjhfkk.router.Router;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentHuiMin extends XFragment<HomePagePresentHuiMin> {

    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_fl)
    public View noDataFl;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.rvy)
    RecyclerView rvy;

    private Bundle bundle, webBundle;
    private GoodsHuiMinModel goodsModel;
    private ImageAdapter imageAdapter;
    private GoodsItemHuiMinAdapter1 miaoJieGoodsItemAdapter1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public HomePagePresentHuiMin newP() {
        return new HomePagePresentHuiMin();
    }

    private void productClick(GoodsHuiMinModel model){
        if (model != null) {
            getP().productClick(model);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
        parentFl.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(goodsModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().productList();
    }

    public void jumpWebYouXinActivity (GoodsHuiMinModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            StaticUtilHuiMin.getValue((XActivity) getActivity(), WebHuiMinActivity.class, webBundle);
        }
    }

//    public void initBannerAdapter(List<GoodsHuiMinModel> data) {
//        imageAdapter = null;
//        imageAdapter = new ImageAdapter(data);
//        imageAdapter.setBannerClickedListener(entity -> {
//            if (entity != null) {
//                productClick(entity);
//            }
//        });
//        banner.setAdapter(imageAdapter);
//    }

    public void initGoodsItemAdapter(List<GoodsHuiMinModel> mData) {
        if (miaoJieGoodsItemAdapter1 == null) {
            miaoJieGoodsItemAdapter1 = new GoodsItemHuiMinAdapter1(getActivity());
            miaoJieGoodsItemAdapter1.setRecItemClick(new RecyclerItemCallback<GoodsHuiMinModel, GoodsItemHuiMinAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsHuiMinModel model, int tag, GoodsItemHuiMinAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            miaoJieGoodsItemAdapter1.setHasStableIds(true);
            miaoJieGoodsItemAdapter1.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(miaoJieGoodsItemAdapter1);
        } else {
            miaoJieGoodsItemAdapter1.setData(mData);
        }
    }

    public void setModel(GoodsHuiMinModel goodsModel){
        this.goodsModel = goodsModel;
    }
}
