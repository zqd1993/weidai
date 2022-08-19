package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviof;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.R;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.JumpH5FenQiXiangVioActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.imageloader.ILFactory;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.imageloader.ILoader;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioBaseModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.ProductModelFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.mvp.XFragment;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.ApiSubscriber;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.NetError;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.XApi;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.FenQiXiangVioOpenUtil;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;

import java.util.List;

import butterknife.BindView;

public class ProductFragmentFenQiXiangVio extends XFragment {

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
    private ProductModelFenQiXiangVio productModelFenQiXiangVio;

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
            productClick(productModelFenQiXiangVio);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModelFenQiXiangVio);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelFenQiXiangVio);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_xiang_qi_vio_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelFenQiXiangVio model) {
            if (model != null) {
                phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
                HttpApiFenQiXiangVio.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(FenQiXiangVioBaseModel fenQiXiangVioBaseModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilFenQiXiangVio.getInt("mobileType");
            phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
            productModelFenQiXiangVio = null;
            HttpApiFenQiXiangVio.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<List<ProductModelFenQiXiangVio>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            FenQiXiangVioOpenUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel<List<ProductModelFenQiXiangVio>> fenQiXiangVioBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (fenQiXiangVioBaseModel != null) {
                                if (fenQiXiangVioBaseModel.getCode() == 200 && fenQiXiangVioBaseModel.getData() != null) {
                                    if (fenQiXiangVioBaseModel.getData() != null && fenQiXiangVioBaseModel.getData().size() > 0) {
                                        productModelFenQiXiangVio = fenQiXiangVioBaseModel.getData().get(0);
                                        addProductView(fenQiXiangVioBaseModel.getData());
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

    private void addProductView(List<ProductModelFenQiXiangVio> mList) {
        for (ProductModelFenQiXiangVio model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_fen_xiang_qi_vio, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpApiFenQiXiangVio.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductModelFenQiXiangVio model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            FenQiXiangVioOpenUtil.jumpPage(getActivity(), JumpH5FenQiXiangVioActivity.class, bundle);
        }
    }
}
