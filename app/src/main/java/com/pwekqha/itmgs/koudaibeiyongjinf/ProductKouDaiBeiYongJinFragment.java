package com.pwekqha.itmgs.koudaibeiyongjinf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pwekqha.itmgs.R;
import com.pwekqha.itmgs.koudaibeiyongjina.GoodsItemKouDaiBeiYongJinAdapter;
import com.pwekqha.itmgs.koudaibeiyongjina.JumpH5KouDaiBeiYongJinActivity;
import com.pwekqha.itmgs.koudaibeiyongjinapi.HttpKouDaiBeiYongJinApi;
import com.pwekqha.itmgs.koudaibeiyongjinm.BaseKouDaiBeiYongJinModel;
import com.pwekqha.itmgs.koudaibeiyongjinm.ProductKouDaiBeiYongJinModel;
import com.pwekqha.itmgs.mvp.XFragment;
import com.pwekqha.itmgs.net.ApiSubscriber;
import com.pwekqha.itmgs.net.NetError;
import com.pwekqha.itmgs.net.XApi;
import com.pwekqha.itmgs.koudaibeiyongjinu.OpenUtilKouDaiBeiYongJin;
import com.pwekqha.itmgs.koudaibeiyongjinu.KouDaiBeiYongJinPreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductKouDaiBeiYongJinFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.click_fl)
    View click_fl;
    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    private ProductKouDaiBeiYongJinModel productKouDaiBeiYongJinModel;

    private Bundle bundle;

    private GoodsItemKouDaiBeiYongJinAdapter goodsItemKouDaiBeiYongJinAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productKouDaiBeiYongJinModel);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productKouDaiBeiYongJinModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kou_dai_bei_yong_jin_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductKouDaiBeiYongJinModel model) {
            if (model != null) {
                phone = KouDaiBeiYongJinPreferencesOpenUtil.getString("phone");
                HttpKouDaiBeiYongJinApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseKouDaiBeiYongJinModel baseKouDaiBeiYongJinModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = KouDaiBeiYongJinPreferencesOpenUtil.getInt("mobileType");
            phone = KouDaiBeiYongJinPreferencesOpenUtil.getString("phone");
            productKouDaiBeiYongJinModel = null;
            HttpKouDaiBeiYongJinApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel<List<ProductKouDaiBeiYongJinModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenUtilKouDaiBeiYongJin.showErrorInfo(getActivity(), error);
                            noDataTv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinModel<List<ProductKouDaiBeiYongJinModel>> baseKouDaiBeiYongJinModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseKouDaiBeiYongJinModel != null) {
                                if (baseKouDaiBeiYongJinModel.getCode() == 200 && baseKouDaiBeiYongJinModel.getData() != null) {
                                    if (baseKouDaiBeiYongJinModel.getData() != null && baseKouDaiBeiYongJinModel.getData().size() > 0) {
                                        productKouDaiBeiYongJinModel = baseKouDaiBeiYongJinModel.getData().get(0);
                                        initAdapter(baseKouDaiBeiYongJinModel.getData());
                                    } else {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            } else {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    });
    }

    private void initAdapter(List<ProductKouDaiBeiYongJinModel> goods){
        if (goodsItemKouDaiBeiYongJinAdapter == null){
            goodsItemKouDaiBeiYongJinAdapter = new GoodsItemKouDaiBeiYongJinAdapter(getActivity());
            goodsItemKouDaiBeiYongJinAdapter.setData(goods);
            goodsItemKouDaiBeiYongJinAdapter.setRecItemClick(new RecyclerItemCallback<ProductKouDaiBeiYongJinModel, GoodsItemKouDaiBeiYongJinAdapter.GoodsItemHolder>() {
                @Override
                public void onItemClick(int position, ProductKouDaiBeiYongJinModel model, int tag, GoodsItemKouDaiBeiYongJinAdapter.GoodsItemHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(productKouDaiBeiYongJinModel);
                }
            });
            goodsItemKouDaiBeiYongJinAdapter.setHasStableIds(true);
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsItemKouDaiBeiYongJinAdapter);
        } else {
            goodsItemKouDaiBeiYongJinAdapter.setData(goods);
        }
    }

    public void toWeb(ProductKouDaiBeiYongJinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), JumpH5KouDaiBeiYongJinActivity.class, bundle);
        }
    }
}
