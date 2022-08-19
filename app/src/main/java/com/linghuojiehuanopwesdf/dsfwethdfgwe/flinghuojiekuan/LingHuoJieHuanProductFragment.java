package com.linghuojiehuanopwesdf.dsfwethdfgwe.flinghuojiekuan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.JumpH5LingHuoJieHuanActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.ProductGoodsItemAdapter;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan.HttpLingHuoJieHuanApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.imageloader.ILFactory;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.imageloader.ILoader;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.BaseModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ProductLingHuoJieHuanModel;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mvp.XActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mvp.XFragment;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.ApiSubscriber;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.NetError;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.XApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanOpenUtil;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.PreferencesOpenUtilLingHuoJieHuan;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class LingHuoJieHuanProductFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.click_fl)
    View click_fl;
    @BindView(R.id.goods_list)
    RecyclerView goods_list;
    @BindView(R.id.main_top_img)
    View main_top_img;
    private ProductLingHuoJieHuanModel productLingHuoJieHuanModel;

    private Bundle bundle;

    private ProductGoodsItemAdapter productGoodsItemAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productLingHuoJieHuanModel);
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productLingHuoJieHuanModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_ling_huo_jie_huan;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductLingHuoJieHuanModel model) {
            if (model != null) {
                phone = PreferencesOpenUtilLingHuoJieHuan.getString("phone");
                HttpLingHuoJieHuanApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseModelLingHuoJieHuan baseModelLingHuoJieHuan) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilLingHuoJieHuan.getInt("mobileType");
            phone = PreferencesOpenUtilLingHuoJieHuan.getString("phone");
            productLingHuoJieHuanModel = null;
            HttpLingHuoJieHuanApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan<List<ProductLingHuoJieHuanModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            LingHuoJieHuanOpenUtil.showErrorInfo(getActivity(), error);
                            noDataTv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan<List<ProductLingHuoJieHuanModel>> baseModelLingHuoJieHuan) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelLingHuoJieHuan != null) {
                                if (baseModelLingHuoJieHuan.getCode() == 200 && baseModelLingHuoJieHuan.getData() != null) {
                                    if (baseModelLingHuoJieHuan.getData() != null && baseModelLingHuoJieHuan.getData().size() > 0) {
                                        productLingHuoJieHuanModel = baseModelLingHuoJieHuan.getData().get(0);
                                        initAdapter(baseModelLingHuoJieHuan.getData());
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

    private void initAdapter(List<ProductLingHuoJieHuanModel> mList){
        if (productGoodsItemAdapter == null){
            productGoodsItemAdapter = new ProductGoodsItemAdapter(getActivity());
            productGoodsItemAdapter.setData(mList);
            productGoodsItemAdapter.setHasStableIds(true);
            productGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<ProductLingHuoJieHuanModel, ProductGoodsItemAdapter.ProductGoodsItemHolder>() {
                @Override
                public void onItemClick(int position, ProductLingHuoJieHuanModel model, int tag, ProductGoodsItemAdapter.ProductGoodsItemHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goods_list.setHasFixedSize(true);
            goods_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goods_list.setAdapter(productGoodsItemAdapter);
        } else {
            productGoodsItemAdapter.setData(mList);
        }
    }

    private void addProductView(List<ProductLingHuoJieHuanModel> mList) {
        for (ProductLingHuoJieHuanModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_ling_huo_jie_huan_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            ILFactory.getLoader().loadNet(pic, HttpLingHuoJieHuanApi.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            shijian_tv.setText(model.getDes());
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
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
        }
    }

    public void toWeb(ProductLingHuoJieHuanModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            LingHuoJieHuanOpenUtil.jumpPage((XActivity) getActivity(), JumpH5LingHuoJieHuanActivity.class, bundle);
        }
    }
}
