package com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.GoodsKuaiJieKuanAdapter;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.JumpH5ActivityKuaiJieKuan;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.KuaiJieKuanBaseModel;
import com.fjsdkqwj.pfdioewjnsd.imageloader.ILFactory;
import com.fjsdkqwj.pfdioewjnsd.imageloader.ILoader;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.ProductKuaiJieKuanModel;
import com.fjsdkqwj.pfdioewjnsd.mvp.XFragment;
import com.fjsdkqwj.pfdioewjnsd.net.ApiSubscriber;
import com.fjsdkqwj.pfdioewjnsd.net.NetError;
import com.fjsdkqwj.pfdioewjnsd.net.XApi;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau.OpenKuaiJieKuanUtil;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau.PreferencesOpenUtilKuaiJieKuan;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductFragmentKuaiJieKuan extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    RecyclerView goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.click_fl)
    View click_fl;
    private ProductKuaiJieKuanModel productKuaiJieKuanModel;

    private Bundle bundle;
    private GoodsKuaiJieKuanAdapter goodsAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productKuaiJieKuanModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productKuaiJieKuanModel);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productKuaiJieKuanModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kuai_jie_kuan_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductKuaiJieKuanModel model) {
            if (model != null) {
                phone = PreferencesOpenUtilKuaiJieKuan.getString("phone");
                KuaiJieKuanHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(KuaiJieKuanBaseModel kuaiJieKuanBaseModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilKuaiJieKuan.getInt("mobileType");
            phone = PreferencesOpenUtilKuaiJieKuan.getString("phone");
            productKuaiJieKuanModel = null;
            KuaiJieKuanHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel<List<ProductKuaiJieKuanModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenKuaiJieKuanUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel<List<ProductKuaiJieKuanModel>> kuaiJieKuanBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (kuaiJieKuanBaseModel != null) {
                                if (kuaiJieKuanBaseModel.getCode() == 200 && kuaiJieKuanBaseModel.getData() != null) {
                                    if (kuaiJieKuanBaseModel.getData() != null && kuaiJieKuanBaseModel.getData().size() > 0) {
                                        productKuaiJieKuanModel = kuaiJieKuanBaseModel.getData().get(0);
//                                        addProductView(kuaiJieKuanBaseModel.getData());
                                        initAdapter(kuaiJieKuanBaseModel.getData());
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

    private void addProductView(List<ProductKuaiJieKuanModel> mList) {
        for (ProductKuaiJieKuanModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_kuai_jie_kuan_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, KuaiJieKuanHttpApi.HTTP_API_URL + model.getProductLogo(),
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

    private void initAdapter(List<ProductKuaiJieKuanModel> mList){
        if (goodsAdapter ==  null){
            goodsAdapter = new GoodsKuaiJieKuanAdapter(getActivity());
            goodsAdapter.setData(mList);
            goodsAdapter.setHasStableIds(true);
            goodsAdapter.setRecItemClick(new RecyclerItemCallback<ProductKuaiJieKuanModel, GoodsKuaiJieKuanAdapter.GoodsHolder>() {
                @Override
                public void onItemClick(int position, ProductKuaiJieKuanModel model, int tag, GoodsKuaiJieKuanAdapter.GoodsHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsListLl.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goodsListLl.setHasFixedSize(true);
            goodsListLl.setAdapter(goodsAdapter);
        } else {
            goodsAdapter.setData(mList);
        }
    }

    public void toWeb(ProductKuaiJieKuanModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenKuaiJieKuanUtil.jumpPage(getActivity(), JumpH5ActivityKuaiJieKuan.class, bundle);
        }
    }
}
