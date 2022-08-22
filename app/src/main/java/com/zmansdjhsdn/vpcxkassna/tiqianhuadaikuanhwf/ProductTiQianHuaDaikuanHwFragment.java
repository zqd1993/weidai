package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zmansdjhsdn.vpcxkassna.R;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.JumpTiQianHuaDaikuanHwH5Activity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwapi.HttpTiQianHuaDaikuanHwApi;
import com.zmansdjhsdn.vpcxkassna.imageloader.ILFactory;
import com.zmansdjhsdn.vpcxkassna.imageloader.ILoader;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.BaseTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ProductModelTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.mvp.XFragment;
import com.zmansdjhsdn.vpcxkassna.net.ApiSubscriber;
import com.zmansdjhsdn.vpcxkassna.net.NetError;
import com.zmansdjhsdn.vpcxkassna.net.XApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.OpenTiQianHuaDaikuanHwUtil;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.PreferencesOpenUtilTiQianHuaDaikuanHw;

import java.util.List;

import butterknife.BindView;

public class ProductTiQianHuaDaikuanHwFragment extends XFragment {

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
    private ProductModelTiQianHuaDaikuanHw productModelTiQianHuaDaikuanHw;

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
            productClick(productModelTiQianHuaDaikuanHw);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModelTiQianHuaDaikuanHw);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelTiQianHuaDaikuanHw);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_ti_qian_hua_dai_kuan_hw;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelTiQianHuaDaikuanHw model) {
            if (model != null) {
                phone = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("phone");
                HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseTiQianHuaDaikuanHwModel baseTiQianHuaDaikuanHwModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilTiQianHuaDaikuanHw.getInt("mobileType");
            phone = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("phone");
            productModelTiQianHuaDaikuanHw = null;
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel<List<ProductModelTiQianHuaDaikuanHw>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenTiQianHuaDaikuanHwUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel<List<ProductModelTiQianHuaDaikuanHw>> baseTiQianHuaDaikuanHwModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (baseTiQianHuaDaikuanHwModel != null) {
                                if (baseTiQianHuaDaikuanHwModel.getCode() == 200 && baseTiQianHuaDaikuanHwModel.getData() != null) {
                                    if (baseTiQianHuaDaikuanHwModel.getData() != null && baseTiQianHuaDaikuanHwModel.getData().size() > 0) {
                                        productModelTiQianHuaDaikuanHw = baseTiQianHuaDaikuanHwModel.getData().get(0);
                                        addProductView(baseTiQianHuaDaikuanHwModel.getData());
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

    private void addProductView(List<ProductModelTiQianHuaDaikuanHw> mList) {
        for (int i = 0; i < mList.size(); i++) {
            ProductModelTiQianHuaDaikuanHw model = mList.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_ti_qian_hua_dai_kuan_hw_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            ImageView pic1 = view.findViewById(R.id.product_img_1);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpTiQianHuaDaikuanHwApi.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            ILFactory.getLoader().loadNet(pic1, HttpTiQianHuaDaikuanHwApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            if (i % 2 == 0){
                pic.setVisibility(View.GONE);
                pic1.setVisibility(View.VISIBLE);
            } else {
                pic.setVisibility(View.VISIBLE);
                pic1.setVisibility(View.GONE);
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

    public void toWeb(ProductModelTiQianHuaDaikuanHw model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), JumpTiQianHuaDaikuanHwH5Activity.class, bundle);
        }
    }
}
