package com.xvhyrt.ghjtyu.f;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.a.ImageAdapter;
import com.xvhyrt.ghjtyu.a.JumpH5Activity;
import com.xvhyrt.ghjtyu.api.HttpApi;
import com.xvhyrt.ghjtyu.m.BaseModel;
import com.xvhyrt.ghjtyu.m.ProductModel;
import com.xvhyrt.ghjtyu.mvp.XFragment;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.OpenUtil;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.banner_fl)
    View banner_fl;

    private ProductModel productModel;

    private Bundle bundle;

    private ImageAdapter imageAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        banner_fl.setVisibility(View.VISIBLE);
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
    }

    private void initBannerAdapter(List<ProductModel> data) {
        imageAdapter = null;
        imageAdapter = new ImageAdapter(data);
        imageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapter);
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
            if (model == null) {
                return;
            }
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
                            if (imageAdapter == null) {
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
                                        initBannerAdapter(baseModel.getData());
                                    } else {
                                        if (imageAdapter == null) {
                                            noDataTv.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (imageAdapter == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapter == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
        }
    }

    public void toWeb(ProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtil.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }
}
