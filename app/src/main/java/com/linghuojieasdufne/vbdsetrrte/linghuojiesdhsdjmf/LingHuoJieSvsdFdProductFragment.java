package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.JumpH5ActivityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.ProductGoodsItemAdapterLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi.HttpApiLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.imageloader.ILFactory;
import com.linghuojieasdufne.vbdsetrrte.imageloader.ILoader;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.BaseModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ProductModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.mvp.XActivity;
import com.linghuojieasdufne.vbdsetrrte.mvp.XFragment;
import com.linghuojieasdufne.vbdsetrrte.net.ApiSubscriber;
import com.linghuojieasdufne.vbdsetrrte.net.NetError;
import com.linghuojieasdufne.vbdsetrrte.net.XApi;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdOpenUtil;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.PreferencesOpenUtilLingHuoJieSvsdFd;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class LingHuoJieSvsdFdProductFragment extends XFragment {

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
    private ProductModelLingHuoJieSvsdFd productModelLingHuoJieSvsdFd;

    private Bundle bundle;

    private ProductGoodsItemAdapterLingHuoJieSvsdFd productGoodsItemAdapterLingHuoJieSvsdFd;

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
            productClick(productModelLingHuoJieSvsdFd);
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelLingHuoJieSvsdFd);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_ling_huo_jie_djs_urng;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelLingHuoJieSvsdFd model) {
            if (model != null) {
                phone = PreferencesOpenUtilLingHuoJieSvsdFd.getString("phone");
                HttpApiLingHuoJieSvsdFd.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseModelLingHuoJieSvsdFd baseModelLingHuoJieSvsdFd) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilLingHuoJieSvsdFd.getInt("mobileType");
            phone = PreferencesOpenUtilLingHuoJieSvsdFd.getString("phone");
            productModelLingHuoJieSvsdFd = null;
            HttpApiLingHuoJieSvsdFd.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<List<ProductModelLingHuoJieSvsdFd>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            LingHuoJieSvsdFdOpenUtil.showErrorInfo(getActivity(), error);
                            noDataTv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd<List<ProductModelLingHuoJieSvsdFd>> baseModelLingHuoJieSvsdFd) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelLingHuoJieSvsdFd != null) {
                                if (baseModelLingHuoJieSvsdFd.getCode() == 200 && baseModelLingHuoJieSvsdFd.getData() != null) {
                                    if (baseModelLingHuoJieSvsdFd.getData() != null && baseModelLingHuoJieSvsdFd.getData().size() > 0) {
                                        productModelLingHuoJieSvsdFd = baseModelLingHuoJieSvsdFd.getData().get(0);
                                        initAdapter(baseModelLingHuoJieSvsdFd.getData());
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

    private void initAdapter(List<ProductModelLingHuoJieSvsdFd> mList){
        if (productGoodsItemAdapterLingHuoJieSvsdFd == null){
            productGoodsItemAdapterLingHuoJieSvsdFd = new ProductGoodsItemAdapterLingHuoJieSvsdFd(getActivity());
            productGoodsItemAdapterLingHuoJieSvsdFd.setData(mList);
            productGoodsItemAdapterLingHuoJieSvsdFd.setHasStableIds(true);
            productGoodsItemAdapterLingHuoJieSvsdFd.setRecItemClick(new RecyclerItemCallback<ProductModelLingHuoJieSvsdFd, ProductGoodsItemAdapterLingHuoJieSvsdFd.ProductGoodsItemHolder>() {
                @Override
                public void onItemClick(int position, ProductModelLingHuoJieSvsdFd model, int tag, ProductGoodsItemAdapterLingHuoJieSvsdFd.ProductGoodsItemHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goods_list.setHasFixedSize(true);
            goods_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goods_list.setAdapter(productGoodsItemAdapterLingHuoJieSvsdFd);
        } else {
            productGoodsItemAdapterLingHuoJieSvsdFd.setData(mList);
        }
    }

    private void addProductView(List<ProductModelLingHuoJieSvsdFd> mList) {
        for (ProductModelLingHuoJieSvsdFd model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_ling_huo_jie_djs_urng_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            ILFactory.getLoader().loadNet(pic, HttpApiLingHuoJieSvsdFd.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductModelLingHuoJieSvsdFd model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            LingHuoJieSvsdFdOpenUtil.jumpPage((XActivity) getActivity(), JumpH5ActivityLingHuoJieSvsdFd.class, bundle);
        }
    }
}
