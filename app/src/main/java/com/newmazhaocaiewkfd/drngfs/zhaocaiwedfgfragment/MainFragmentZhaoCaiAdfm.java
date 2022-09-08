package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.ImageAdapterZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.MainZhaoCaiAdfmActivity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.ZhaoCaiAdfmJumpH5Activity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgapi.ZhaoCaiAdfmHttpApi;
import com.newmazhaocaiewkfd.drngfs.imageloader.ILFactory;
import com.newmazhaocaiewkfd.drngfs.imageloader.ILoader;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BannerModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BaseModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ProductModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.mvp.XActivity;
import com.newmazhaocaiewkfd.drngfs.mvp.XFragment;
import com.newmazhaocaiewkfd.drngfs.net.ApiSubscriber;
import com.newmazhaocaiewkfd.drngfs.net.NetError;
import com.newmazhaocaiewkfd.drngfs.net.XApi;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.OpenZhaoCaiAdfmUtil;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFragmentZhaoCaiAdfm extends XFragment {

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

    private ProductModelZhaoCaiAdfm productModelZhaoCaiAdfm;

    private Bundle bundle;

    private ImageAdapterZhaoCaiAdfm imageAdapterZhaoCaiAdfm;

    @Override
    public void initData(Bundle savedInstanceState) {
//        banner_fl.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
                bannerList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelZhaoCaiAdfm);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelZhaoCaiAdfm);
        });
        mine_tv.setOnClickListener(v -> {
            ((MainZhaoCaiAdfmActivity) getActivity()).jumpMine();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    private void initBannerAdapter(List<ProductModelZhaoCaiAdfm> data) {
        imageAdapterZhaoCaiAdfm = null;
        imageAdapterZhaoCaiAdfm = new ImageAdapterZhaoCaiAdfm(data);
        imageAdapterZhaoCaiAdfm.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterZhaoCaiAdfm);
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
        if (model == null) {
            return;
        }
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


    public void productList() {
        mobileType = PreferencesOpenUtilZhaoCaiAdfm.getInt("mobileType");
        phone = PreferencesOpenUtilZhaoCaiAdfm.getString("phone");
        productModelZhaoCaiAdfm = null;
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<List<ProductModelZhaoCaiAdfm>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenZhaoCaiAdfmUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapterZhaoCaiAdfm == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<List<ProductModelZhaoCaiAdfm>> baseModelZhaoCaiAdfm) {
                        setRefreshing.setRefreshing(false);
                        goodsListLl.removeAllViews();
                        if (baseModelZhaoCaiAdfm != null) {
                            if (baseModelZhaoCaiAdfm.getCode() == 200 && baseModelZhaoCaiAdfm.getData() != null) {
                                if (baseModelZhaoCaiAdfm.getData() != null && baseModelZhaoCaiAdfm.getData().size() > 0) {
                                    productModelZhaoCaiAdfm = baseModelZhaoCaiAdfm.getData().get(0);
//                                        initBannerAdapter(baseModelZhaoCaiAdfm.getData());
                                    addProductView(baseModelZhaoCaiAdfm.getData());
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

    private void bannerList() {
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().bannerList()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<List<BannerModelZhaoCaiAdfm>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenZhaoCaiAdfmUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<List<BannerModelZhaoCaiAdfm>> baseModelZhaoCaiAdfm) {
                        if (baseModelZhaoCaiAdfm != null) {
                            if (baseModelZhaoCaiAdfm.getCode() == 200) {
//                                if (baseModelZhaoCaiAdfm.getData() != null && baseModelZhaoCaiAdfm.getData().size() > 0) {
//                                    ILFactory.getLoader().loadNet(banner_img, ZhaoCaiAdfmHttpApi.HTTP_API_URL + baseModelZhaoCaiAdfm.getData().get(0).getLogo(),
//                                            new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
//                                }
                            }
                        }
                    }
                });
    }

    private void addProductView(List<ProductModelZhaoCaiAdfm> mList) {
        for (ProductModelZhaoCaiAdfm model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_zhao_cai_endfi_weng, null);
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
            bundle.putString("biaoti", model.getProductName());
            OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), ZhaoCaiAdfmJumpH5Activity.class, bundle);
        }
    }
}
