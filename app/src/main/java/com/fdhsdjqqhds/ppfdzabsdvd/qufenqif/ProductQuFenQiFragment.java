package com.fdhsdjqqhds.ppfdzabsdvd.qufenqif;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.QuFenQiJumpH5Activity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi.HttpApiQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.imageloader.ILFactory;
import com.fdhsdjqqhds.ppfdzabsdvd.imageloader.ILoader;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.BaseQuFenQiModel;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.ProductModelQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.net.ApiSubscriber;
import com.fdhsdjqqhds.ppfdzabsdvd.net.NetError;
import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.OpenUtilQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;

import java.util.List;

import butterknife.BindView;

public class ProductQuFenQiFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    private ProductModelQuFenQi productModelQuFenQi;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelQuFenQi);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelQuFenQi);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModelQuFenQi);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qu_fen_qi_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelQuFenQi model) {
            if (model != null) {
                phone = PreferencesQuFenQiOpenUtil.getString("phone");
                HttpApiQuFenQi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseQuFenQiModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseQuFenQiModel baseQuFenQiModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesQuFenQiOpenUtil.getInt("mobileType");
            phone = PreferencesQuFenQiOpenUtil.getString("phone");
            HttpApiQuFenQi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQuFenQiModel<List<ProductModelQuFenQi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenUtilQuFenQi.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseQuFenQiModel<List<ProductModelQuFenQi>> baseQuFenQiModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseQuFenQiModel != null) {
                                if (baseQuFenQiModel.getCode() == 200 && baseQuFenQiModel.getData() != null) {
                                    if (baseQuFenQiModel.getData() != null && baseQuFenQiModel.getData().size() > 0) {
                                        productModelQuFenQi = baseQuFenQiModel.getData().get(0);
                                        addProductView(baseQuFenQiModel.getData());
                                    } else {
                                        if (goodsListLl.getChildCount() == 0) {
                                            noDataTv.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (goodsListLl.getChildCount() == 0) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (goodsListLl.getChildCount() == 0) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
    }

    private void addProductView(List<ProductModelQuFenQi> mList) {
        goodsListLl.removeAllViews();
        for (ProductModelQuFenQi model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            shijian_tv.setText(model.getDes() + "个月");
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
                ILFactory.getLoader().loadNet(pic, HttpApiQuFenQi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductModelQuFenQi model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenUtilQuFenQi.getValue((XActivity) getActivity(), QuFenQiJumpH5Activity.class, bundle);
        }
    }
}
