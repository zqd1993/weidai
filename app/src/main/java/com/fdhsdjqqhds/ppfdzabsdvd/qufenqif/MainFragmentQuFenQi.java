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
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.ImageQuFenQiAdapter;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.MainActivityQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.QuFenQiJumpH5Activity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi.HttpApiQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.imageloader.ILFactory;
import com.fdhsdjqqhds.ppfdzabsdvd.imageloader.ILoader;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.BannerModelQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.BaseQuFenQiModel;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.ProductModelQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.net.ApiSubscriber;
import com.fdhsdjqqhds.ppfdzabsdvd.net.NetError;
import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.OpenUtilQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFragmentQuFenQi extends XFragment {

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
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.banner_img)
    ImageView banner_img;
    @BindView(R.id.mine_tv)
    TextView mine_tv;

    private ProductModelQuFenQi productModelQuFenQi;

    private Bundle bundle;

    private ImageQuFenQiAdapter imageQuFenQiAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
//        banner_fl.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
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
        mine_tv.setOnClickListener(v -> {
            ((MainActivityQuFenQi) getActivity()).jumpMine();
        });
        banner_img.setOnClickListener(v -> {
            productClick(productModelQuFenQi);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    private void initBannerAdapter(List<ProductModelQuFenQi> data) {
        imageQuFenQiAdapter = null;
        imageQuFenQiAdapter = new ImageQuFenQiAdapter(data);
        imageQuFenQiAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageQuFenQiAdapter);
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
        if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
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
        if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesQuFenQiOpenUtil.getInt("mobileType");
            phone = PreferencesQuFenQiOpenUtil.getString("phone");
            productModelQuFenQi = null;
            HttpApiQuFenQi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQuFenQiModel<List<ProductModelQuFenQi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenUtilQuFenQi.showErrorInfo(getActivity(), error);
                            if (imageQuFenQiAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseQuFenQiModel<List<ProductModelQuFenQi>> baseQuFenQiModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (baseQuFenQiModel != null) {
                                if (baseQuFenQiModel.getCode() == 200 && baseQuFenQiModel.getData() != null) {
                                    if (baseQuFenQiModel.getData() != null && baseQuFenQiModel.getData().size() > 0) {
                                        productModelQuFenQi = baseQuFenQiModel.getData().get(0);
//                                        initBannerAdapter(baseModel.getData());
                                        addProductView(baseQuFenQiModel.getData());
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
    }

    private void bannerList() {
        if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
            HttpApiQuFenQi.getInterfaceUtils().bannerList()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQuFenQiModel<List<BannerModelQuFenQi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilQuFenQi.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseQuFenQiModel<List<BannerModelQuFenQi>> baseQuFenQiModel) {
                            if (baseQuFenQiModel != null) {
                                if (baseQuFenQiModel.getCode() == 200) {
                                    if (baseQuFenQiModel.getData() != null && baseQuFenQiModel.getData().size() > 0) {
                                        if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
                                            ILFactory.getLoader().loadNet(banner_img, PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL") + baseQuFenQiModel.getData().get(0).getLogo(),
                                                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void addProductView(List<ProductModelQuFenQi> mList) {
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
            shijian_tv.setText(model.getDes());
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            if (!TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
                ILFactory.getLoader().loadNet(pic, PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL") + model.getProductLogo(),
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

    public void toWeb(ProductModelQuFenQi model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtilQuFenQi.getValue((XActivity) getActivity(), QuFenQiJumpH5Activity.class, bundle);
        }
    }
}
