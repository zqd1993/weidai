package com.xvhyrt.ghjtyu.f;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.a.JumpH5Activity;
import com.xvhyrt.ghjtyu.api.HttpApi;
import com.xvhyrt.ghjtyu.imageloader.ILFactory;
import com.xvhyrt.ghjtyu.imageloader.ILoader;
import com.xvhyrt.ghjtyu.m.BaseModel;
import com.xvhyrt.ghjtyu.m.ProductModel;
import com.xvhyrt.ghjtyu.mvp.XFragment;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.OpenUtil;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;

public class ProductFragment extends XFragment {

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
    private ProductModel productModel;

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
            productClick(productModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModel model) {
        if (!TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
            if (model != null) {
                phone = PreferencesOpenUtil.getString("phone");
                HttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseModel baseModel) {
                                toWeb(model);
                            }
                        });
            }
        }
    }


    public void productList() {
        if (!TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesOpenUtil.getInt("mobileType");
            HttpApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModel<List<ProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseModel<List<ProductModel>> baseModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseModel != null) {
                                if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                    if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                        productModel = baseModel.getData().get(0);
                                        addProductView(baseModel.getData());
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
    }

    private void addProductView(List<ProductModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item, null);
            ImageView pic = view.findViewById(R.id.goods_pic);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            View yjsq_sl = view.findViewById(R.id.yjsq_sl);
            if (!TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
                ILFactory.getLoader().loadNet(pic, PreferencesOpenUtil.getString("HTTP_API_URL") + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            }
            shijian_tv.setText(model.getDes() + "个月");
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsq_sl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    public void toWeb(ProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenUtil.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }
}
