package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.JumpH5MangGuoHWNewActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewapi.HttpApiMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.imageloader.ILFactory;
import com.dqlsdjdhwnew.fdhqwenhwnew.imageloader.ILoader;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewBaseModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.ProductModelMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mvp.XFragment;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.ApiSubscriber;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.NetError;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.XApi;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MangGuoHWNewOpenUtil;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;

import java.util.List;

import butterknife.BindView;

public class ProductFragmentMangGuoHWNew extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.click_fl)
    View click_fl;
    private ProductModelMangGuoHWNew productModelMangGuoHWNew;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelMangGuoHWNew);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModelMangGuoHWNew);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelMangGuoHWNew);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mang_guo_hw_new_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelMangGuoHWNew model) {
            if (model != null) {
                phone = PreferencesOpenUtilMangGuoHWNew.getString("phone");
                HttpApiMangGuoHWNew.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(MangGuoHWNewBaseModel mangGuoHWNewBaseModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilMangGuoHWNew.getInt("mobileType");
            phone = PreferencesOpenUtilMangGuoHWNew.getString("phone");
            productModelMangGuoHWNew = null;
            HttpApiMangGuoHWNew.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel<List<ProductModelMangGuoHWNew>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            MangGuoHWNewOpenUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel<List<ProductModelMangGuoHWNew>> mangGuoHWNewBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (mangGuoHWNewBaseModel != null) {
                                if (mangGuoHWNewBaseModel.getCode() == 200 && mangGuoHWNewBaseModel.getData() != null) {
                                    if (mangGuoHWNewBaseModel.getData() != null && mangGuoHWNewBaseModel.getData().size() > 0) {
                                        productModelMangGuoHWNew = mangGuoHWNewBaseModel.getData().get(0);
                                        addProductView(mangGuoHWNewBaseModel.getData());
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

    private void addProductView(List<ProductModelMangGuoHWNew> mList) {
        for (ProductModelMangGuoHWNew model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_mang_guo_hw_new, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpApiMangGuoHWNew.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsqSl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    public void toWeb(ProductModelMangGuoHWNew model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            MangGuoHWNewOpenUtil.jumpPage(getActivity(), JumpH5MangGuoHWNewActivity.class, bundle);
        }
    }
}
