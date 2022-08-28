package com.biofs.lqgdkic.weifenqijietiaofragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.biofs.lqgdkic.R;
import com.biofs.lqgdkic.weifenqijietiaoactivity.JumpWeiFenQiJieTiaoH5Activity;
import com.biofs.lqgdkic.weifenqijietiaoapi.WeiFenQiJieTiaoApi;
import com.biofs.lqgdkic.imageloader.ILFactory;
import com.biofs.lqgdkic.imageloader.ILoader;
import com.biofs.lqgdkic.weifenqijietiaomodel.BaseModelWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaomodel.ProductWeiFenQiJieTiaoModel;
import com.biofs.lqgdkic.mvp.XActivity;
import com.biofs.lqgdkic.mvp.XFragment;
import com.biofs.lqgdkic.net.ApiSubscriber;
import com.biofs.lqgdkic.net.NetError;
import com.biofs.lqgdkic.net.XApi;
import com.biofs.lqgdkic.weifenqijietiaoutils.OpenWeiFenQiJieTiaoUtil;
import com.biofs.lqgdkic.weifenqijietiaoutils.WeiFenQiJieTiaoPreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;

public class ProductWeiFenQiJieTiaoFragment extends XFragment {

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
    @BindView(R.id.wode_fl)
    View wode_fl;
    private ProductWeiFenQiJieTiaoModel productWeiFenQiJieTiaoModel;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
//        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        wode_fl.setVisibility(View.GONE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productWeiFenQiJieTiaoModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productWeiFenQiJieTiaoModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productWeiFenQiJieTiaoModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_wei_fen_qi_jie_tiao;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductWeiFenQiJieTiaoModel model) {
            if (model != null) {
                phone = WeiFenQiJieTiaoPreferencesOpenUtil.getString("phone");
                WeiFenQiJieTiaoApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseModelWeiFenQiJieTiao baseModelWeiFenQiJieTiao) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = WeiFenQiJieTiaoPreferencesOpenUtil.getInt("mobileType");
            phone = WeiFenQiJieTiaoPreferencesOpenUtil.getString("phone");
            WeiFenQiJieTiaoApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao<List<ProductWeiFenQiJieTiaoModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenWeiFenQiJieTiaoUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseModelWeiFenQiJieTiao<List<ProductWeiFenQiJieTiaoModel>> baseModelWeiFenQiJieTiao) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelWeiFenQiJieTiao != null) {
                                if (baseModelWeiFenQiJieTiao.getCode() == 200 && baseModelWeiFenQiJieTiao.getData() != null) {
                                    if (baseModelWeiFenQiJieTiao.getData() != null && baseModelWeiFenQiJieTiao.getData().size() > 0) {
                                        productWeiFenQiJieTiaoModel = baseModelWeiFenQiJieTiao.getData().get(0);
                                        addProductView(baseModelWeiFenQiJieTiao.getData());
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

    private void addProductView(List<ProductWeiFenQiJieTiaoModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductWeiFenQiJieTiaoModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_wei_fen_qi_jie_tiao_product_item, null);
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
                ILFactory.getLoader().loadNet(pic, WeiFenQiJieTiaoApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductWeiFenQiJieTiaoModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), JumpWeiFenQiJieTiaoH5Activity.class, bundle);
        }
    }
}
