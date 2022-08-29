package com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaif;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dshqbzansdk.vpcvlsdksdhayjtop.R;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.JumpH5QingSongDaiActivity;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiapi.HttpApiQingSongDai;
import com.dshqbzansdk.vpcvlsdksdhayjtop.imageloader.ILFactory;
import com.dshqbzansdk.vpcvlsdksdhayjtop.imageloader.ILoader;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.BaseQingSongDaiModel;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.ProductQingSongDaiModel;
import com.dshqbzansdk.vpcvlsdksdhayjtop.mvp.XFragment;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.ApiSubscriber;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.NetError;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.XApi;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.OpenQingSongDaiUtil;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.PreferencesOpenUtilQingSongDai;

import java.util.List;

import butterknife.BindView;

public class ProductQingSongDaiFragment extends XFragment {

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
    private ProductQingSongDaiModel productQingSongDaiModel;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productQingSongDaiModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productQingSongDaiModel);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productQingSongDaiModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qing_song_dai_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductQingSongDaiModel model) {
            if (model != null) {
                phone = PreferencesOpenUtilQingSongDai.getString("phone");
                HttpApiQingSongDai.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseQingSongDaiModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseQingSongDaiModel baseQingSongDaiModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilQingSongDai.getInt("mobileType");
            phone = PreferencesOpenUtilQingSongDai.getString("phone");
            productQingSongDaiModel = null;
            HttpApiQingSongDai.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel<List<ProductQingSongDaiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenQingSongDaiUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel<List<ProductQingSongDaiModel>> baseQingSongDaiModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (baseQingSongDaiModel != null) {
                                if (baseQingSongDaiModel.getCode() == 200 && baseQingSongDaiModel.getData() != null) {
                                    if (baseQingSongDaiModel.getData() != null && baseQingSongDaiModel.getData().size() > 0) {
                                        productQingSongDaiModel = baseQingSongDaiModel.getData().get(0);
                                        addProductView(baseQingSongDaiModel.getData());
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

    private void addProductView(List<ProductQingSongDaiModel> mList) {
        for (ProductQingSongDaiModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_qing_song_dai, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpApiQingSongDai.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            shijian_tv.setText(model.getDes() + "个月");
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

    public void toWeb(ProductQingSongDaiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenQingSongDaiUtil.jumpPage(getActivity(), JumpH5QingSongDaiActivity.class, bundle);
        }
    }
}
