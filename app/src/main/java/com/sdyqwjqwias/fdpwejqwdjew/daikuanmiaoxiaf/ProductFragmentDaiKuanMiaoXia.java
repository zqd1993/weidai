package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.GoodsDaiKuanMiaoXiaAdapter;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.JumpH5ActivityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi.DaiKuanMiaoXiaHttpApi;
import com.sdyqwjqwias.fdpwejqwdjew.imageloader.ILFactory;
import com.sdyqwjqwias.fdpwejqwdjew.imageloader.ILoader;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DaiKuanMiaoXiaBaseModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ProductDaiKuanMiaoXiaModel;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XFragment;
import com.sdyqwjqwias.fdpwejqwdjew.net.ApiSubscriber;
import com.sdyqwjqwias.fdpwejqwdjew.net.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.OpenDaiKuanMiaoXiaUtil;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.PreferencesOpenUtilDaiKuanMiaoXia;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductFragmentDaiKuanMiaoXia extends XFragment {

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
    private ProductDaiKuanMiaoXiaModel productDaiKuanMiaoXiaModel;

    private Bundle bundle;
    private GoodsDaiKuanMiaoXiaAdapter goodsAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productDaiKuanMiaoXiaModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productDaiKuanMiaoXiaModel);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productDaiKuanMiaoXiaModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dai_kuan_miao_xia_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductDaiKuanMiaoXiaModel model) {
            if (model != null) {
                phone = PreferencesOpenUtilDaiKuanMiaoXia.getString("phone");
                DaiKuanMiaoXiaHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(DaiKuanMiaoXiaBaseModel daiKuanMiaoXiaBaseModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilDaiKuanMiaoXia.getInt("mobileType");
            phone = PreferencesOpenUtilDaiKuanMiaoXia.getString("phone");
            productDaiKuanMiaoXiaModel = null;
            DaiKuanMiaoXiaHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel<List<ProductDaiKuanMiaoXiaModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenDaiKuanMiaoXiaUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel<List<ProductDaiKuanMiaoXiaModel>> daiKuanMiaoXiaBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (daiKuanMiaoXiaBaseModel != null) {
                                if (daiKuanMiaoXiaBaseModel.getCode() == 200 && daiKuanMiaoXiaBaseModel.getData() != null) {
                                    if (daiKuanMiaoXiaBaseModel.getData() != null && daiKuanMiaoXiaBaseModel.getData().size() > 0) {
                                        productDaiKuanMiaoXiaModel = daiKuanMiaoXiaBaseModel.getData().get(0);
//                                        addProductView(daiKuanMiaoXiaBaseModel.getData());
                                        initAdapter(daiKuanMiaoXiaBaseModel.getData());
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

    private void addProductView(List<ProductDaiKuanMiaoXiaModel> mList) {
        for (ProductDaiKuanMiaoXiaModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dai_kuan_miao_xia_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, DaiKuanMiaoXiaHttpApi.HTTP_API_URL + model.getProductLogo(),
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

    private void initAdapter(List<ProductDaiKuanMiaoXiaModel> mList){
        if (goodsAdapter ==  null){
            goodsAdapter = new GoodsDaiKuanMiaoXiaAdapter(getActivity());
            goodsAdapter.setData(mList);
            goodsAdapter.setHasStableIds(true);
            goodsAdapter.setRecItemClick(new RecyclerItemCallback<ProductDaiKuanMiaoXiaModel, GoodsDaiKuanMiaoXiaAdapter.GoodsHolder>() {
                @Override
                public void onItemClick(int position, ProductDaiKuanMiaoXiaModel model, int tag, GoodsDaiKuanMiaoXiaAdapter.GoodsHolder holder) {
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

    public void toWeb(ProductDaiKuanMiaoXiaModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), JumpH5ActivityDaiKuanMiaoXia.class, bundle);
        }
    }
}
