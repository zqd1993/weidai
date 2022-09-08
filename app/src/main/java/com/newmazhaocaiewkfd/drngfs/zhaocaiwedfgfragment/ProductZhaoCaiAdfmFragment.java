package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.ZhaoCaiAdfmJumpH5Activity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgapi.ZhaoCaiAdfmHttpApi;
import com.newmazhaocaiewkfd.drngfs.imageloader.ILFactory;
import com.newmazhaocaiewkfd.drngfs.imageloader.ILoader;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BaseModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ProductModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.mvp.XActivity;
import com.newmazhaocaiewkfd.drngfs.mvp.XFragment;
import com.newmazhaocaiewkfd.drngfs.net.ApiSubscriber;
import com.newmazhaocaiewkfd.drngfs.net.NetError;
import com.newmazhaocaiewkfd.drngfs.net.XApi;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.OpenZhaoCaiAdfmUtil;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;

import java.util.List;

import butterknife.BindView;

public class ProductZhaoCaiAdfmFragment extends XFragment {

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
    @BindView(R.id.main_ll)
    View main_ll;
    private ProductModelZhaoCaiAdfm productModelZhaoCaiAdfm;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        main_ll.setVisibility(View.GONE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelZhaoCaiAdfm);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelZhaoCaiAdfm);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModelZhaoCaiAdfm);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_zhao_cai_endfi_weng;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelZhaoCaiAdfm model) {
        if (model != null) {
            phone = PreferencesOpenUtilZhaoCaiAdfm.getString("phone");
            ZhaoCaiAdfmHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelZhaoCaiAdfm baseModelZhaoCaiAdfm) {
                            toWeb(model);
                        }
                    });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    public void productList() {
        mobileType = PreferencesOpenUtilZhaoCaiAdfm.getInt("mobileType");
        phone = PreferencesOpenUtilZhaoCaiAdfm.getString("phone");
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<List<ProductModelZhaoCaiAdfm>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenZhaoCaiAdfmUtil.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<List<ProductModelZhaoCaiAdfm>> baseModelZhaoCaiAdfm) {
                        setRefreshing.setRefreshing(false);
                        if (baseModelZhaoCaiAdfm != null) {
                            if (baseModelZhaoCaiAdfm.getCode() == 200 && baseModelZhaoCaiAdfm.getData() != null) {
                                if (baseModelZhaoCaiAdfm.getData() != null && baseModelZhaoCaiAdfm.getData().size() > 0) {
                                    productModelZhaoCaiAdfm = baseModelZhaoCaiAdfm.getData().get(0);
                                    addProductView(baseModelZhaoCaiAdfm.getData());
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

    private void addProductView(List<ProductModelZhaoCaiAdfm> mList) {
        goodsListLl.removeAllViews();
        for (ProductModelZhaoCaiAdfm model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_zhao_cai_endfi_weng_1, null);
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
            ILFactory.getLoader().loadNet(pic, ZhaoCaiAdfmHttpApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductModelZhaoCaiAdfm model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), ZhaoCaiAdfmJumpH5Activity.class, bundle);
        }
    }
}
