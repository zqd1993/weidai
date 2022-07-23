package com.qwbasvsd.zmnxcmdsjsdk.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qwbasvsd.zmnxcmdsjsdk.R;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity.JumpLeFenQiNewsH5Activity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiapi.HttpLeFenQiNewsApi;
import com.qwbasvsd.zmnxcmdsjsdk.imageloader.ILFactory;
import com.qwbasvsd.zmnxcmdsjsdk.imageloader.ILoader;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.BaseModelLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.ProductLeFenQiNewsModel;
import com.qwbasvsd.zmnxcmdsjsdk.mvp.XActivity;
import com.qwbasvsd.zmnxcmdsjsdk.mvp.XFragment;
import com.qwbasvsd.zmnxcmdsjsdk.net.ApiSubscriber;
import com.qwbasvsd.zmnxcmdsjsdk.net.NetError;
import com.qwbasvsd.zmnxcmdsjsdk.net.XApi;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.OpenLeFenQiNewsUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.LeFenQiNewsPreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;

public class ProductLeFenQiNewsFragment extends XFragment {

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
    private ProductLeFenQiNewsModel productLeFenQiNewsModel;

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
            productClick(productLeFenQiNewsModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productLeFenQiNewsModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productLeFenQiNewsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_le_fen_qi;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductLeFenQiNewsModel model) {
        if (!TextUtils.isEmpty(LeFenQiNewsPreferencesOpenUtil.getString("HTTP_API_URL"))) {
            if (model != null) {
                phone = LeFenQiNewsPreferencesOpenUtil.getString("phone");
                HttpLeFenQiNewsApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseModelLeFenQiNews>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseModelLeFenQiNews baseModelLeFenQiNews) {
                                toWeb(model);
                            }
                        });
            }
        }
    }


    public void productList() {
        if (!TextUtils.isEmpty(LeFenQiNewsPreferencesOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = LeFenQiNewsPreferencesOpenUtil.getInt("mobileType");
            phone = LeFenQiNewsPreferencesOpenUtil.getString("phone");
            HttpLeFenQiNewsApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeFenQiNews<List<ProductLeFenQiNewsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenLeFenQiNewsUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseModelLeFenQiNews<List<ProductLeFenQiNewsModel>> baseModelLeFenQiNews) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelLeFenQiNews != null) {
                                if (baseModelLeFenQiNews.getCode() == 200 && baseModelLeFenQiNews.getData() != null) {
                                    if (baseModelLeFenQiNews.getData() != null && baseModelLeFenQiNews.getData().size() > 0) {
                                        productLeFenQiNewsModel = baseModelLeFenQiNews.getData().get(0);
                                        addProductView(baseModelLeFenQiNews.getData());
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

    private void addProductView(List<ProductLeFenQiNewsModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductLeFenQiNewsModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_le_fen_qi_product_item, null);
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
            if (!TextUtils.isEmpty(LeFenQiNewsPreferencesOpenUtil.getString("HTTP_API_URL"))) {
                ILFactory.getLoader().loadNet(pic, LeFenQiNewsPreferencesOpenUtil.getString("HTTP_API_URL") + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            }
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

    public void toWeb(ProductLeFenQiNewsModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), JumpLeFenQiNewsH5Activity.class, bundle);
        }
    }
}
