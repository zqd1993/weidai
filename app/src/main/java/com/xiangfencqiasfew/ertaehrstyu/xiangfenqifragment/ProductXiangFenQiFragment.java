package com.xiangfencqiasfew.ertaehrstyu.xiangfenqifragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xiangfencqiasfew.ertaehrstyu.R;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.XiangFenQiJumpH5Activity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiapi.XiangFenQiHttpApi;
import com.xiangfencqiasfew.ertaehrstyu.imageloader.ILFactory;
import com.xiangfencqiasfew.ertaehrstyu.imageloader.ILoader;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BaseModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ProductModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XActivity;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XFragment;
import com.xiangfencqiasfew.ertaehrstyu.net.ApiSubscriber;
import com.xiangfencqiasfew.ertaehrstyu.net.NetError;
import com.xiangfencqiasfew.ertaehrstyu.net.XApi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.OpenXiangFenQiUtil;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.PreferencesOpenUtilXiangFenQi;

import java.util.List;

import butterknife.BindView;

public class ProductXiangFenQiFragment extends XFragment {

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
    private ProductModelXiangFenQi productModelXiangFenQi;

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
            productClick(productModelXiangFenQi);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelXiangFenQi);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModelXiangFenQi);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_xiang_fen_qi;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelXiangFenQi model) {
        if (model != null) {
            phone = PreferencesOpenUtilXiangFenQi.getString("phone");
            XiangFenQiHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelXiangFenQi>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelXiangFenQi baseModelXiangFenQi) {
                            toWeb(model);
                        }
                    });
        }
    }


    public void productList() {
        mobileType = PreferencesOpenUtilXiangFenQi.getInt("mobileType");
        phone = PreferencesOpenUtilXiangFenQi.getString("phone");
        XiangFenQiHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<List<ProductModelXiangFenQi>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenXiangFenQiUtil.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<List<ProductModelXiangFenQi>> baseModelXiangFenQi) {
                        setRefreshing.setRefreshing(false);
                        if (baseModelXiangFenQi != null) {
                            if (baseModelXiangFenQi.getCode() == 200 && baseModelXiangFenQi.getData() != null) {
                                if (baseModelXiangFenQi.getData() != null && baseModelXiangFenQi.getData().size() > 0) {
                                    productModelXiangFenQi = baseModelXiangFenQi.getData().get(0);
                                    addProductView(baseModelXiangFenQi.getData());
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

    private void addProductView(List<ProductModelXiangFenQi> mList) {
        goodsListLl.removeAllViews();
        for (ProductModelXiangFenQi model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_xiang_fen_qi, null);
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
            ILFactory.getLoader().loadNet(pic, XiangFenQiHttpApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductModelXiangFenQi model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenXiangFenQiUtil.getValue((XActivity) getActivity(), XiangFenQiJumpH5Activity.class, bundle);
        }
    }
}
