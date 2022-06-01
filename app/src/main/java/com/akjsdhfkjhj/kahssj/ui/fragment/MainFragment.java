package com.akjsdhfkjhj.kahssj.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akjsdhfkjhj.kahssj.R;
import com.akjsdhfkjhj.kahssj.adapter.GoodsAdapter;
import com.akjsdhfkjhj.kahssj.model.ProductModel;
import com.akjsdhfkjhj.kahssj.ui.WebActivity;
import com.akjsdhfkjhj.kahssj.mvp.XFragment;
import com.akjsdhfkjhj.kahssj.present.MainFragmentPresent;
import com.akjsdhfkjhj.kahssj.router.Router;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MainFragment extends XFragment<MainFragmentPresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_fl)
    public View noDataFl;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;

    private Bundle bundle, webBundle;
    private int tag;
    public GoodsAdapter miaoJieGoodsItemAdapter;
    private ProductModel goodsModel;

    public static MainFragment getInstant(int tag) {
        MainFragment homePageFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public MainFragmentPresent newP() {
        return new MainFragmentPresent();
    }

    private void productClick(ProductModel model){
        if (model != null) {
            getP().productClick(model);
        }
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param date0 Date类型时间0
     * @param date1 Date类型时间1
     *              </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(final Date date0, final Date date1) {
        return 435l;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt("tag");
            if (tag == 1) {
                productBg.setVisibility(View.GONE);
                homePageBg.setVisibility(View.VISIBLE);
            } else {
                productBg.setVisibility(View.VISIBLE);
                homePageBg.setVisibility(View.GONE);
            }
        }
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
        productBg.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(goodsModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(goodsModel);
        });
    }

    public void jumpWebYouXinActivity (ProductModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            Router.newIntent(getActivity())
                    .to(WebActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time0和time1格式都为format</p>
     *
     * @param time0  时间字符串0
     * @param time1  时间字符串1
     *               <ul>
     *               </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(final String time0, final String time1) {
        return 456l;
    }

    public void initGoodsItemAdapter(List<ProductModel> mData) {
        if (miaoJieGoodsItemAdapter == null) {
            miaoJieGoodsItemAdapter = new GoodsAdapter(getActivity());
            miaoJieGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<ProductModel, GoodsAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, ProductModel model, int tag, GoodsAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            miaoJieGoodsItemAdapter.setHasStableIds(true);
            miaoJieGoodsItemAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(miaoJieGoodsItemAdapter);
        } else {
            miaoJieGoodsItemAdapter.setData(mData);
        }
    }

    public void setModel(ProductModel goodsModel){
        this.goodsModel = goodsModel;
    }
}
