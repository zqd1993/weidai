package com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaif;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dshqbzansdk.vpcvlsdksdhayjtop.R;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.ImageQingSongDaiAdapter;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.JumpH5QingSongDaiActivity;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiapi.HttpApiQingSongDai;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.BaseQingSongDaiModel;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.ProductQingSongDaiModel;
import com.dshqbzansdk.vpcvlsdksdhayjtop.mvp.XFragment;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.ApiSubscriber;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.NetError;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.XApi;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.OpenQingSongDaiUtil;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.PreferencesOpenUtilQingSongDai;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainQingSongDaiFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.click_fl)
    View click_fl;

    private ProductQingSongDaiModel productQingSongDaiModel;

    private Bundle bundle;

    private ImageQingSongDaiAdapter imageQingSongDaiAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        banner.setBannerGalleryEffect(50, 10);
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
            productClick(productQingSongDaiModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductQingSongDaiModel> data) {
        imageQingSongDaiAdapter = null;
        imageQingSongDaiAdapter = new ImageQingSongDaiAdapter(data);
        imageQingSongDaiAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageQingSongDaiAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_qing_song_dai;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductQingSongDaiModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilQingSongDai.getString("phone");
            HttpApiQingSongDai.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel baseQingSongDaiModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencesOpenUtilQingSongDai.getInt("mobileType");
            phone = PreferencesOpenUtilQingSongDai.getString("phone");
            productQingSongDaiModel = null;
            HttpApiQingSongDai.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel<List<ProductQingSongDaiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenQingSongDaiUtil.showErrorInfo(getActivity(), error);
                            if (imageQingSongDaiAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel<List<ProductQingSongDaiModel>> baseQingSongDaiModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseQingSongDaiModel != null) {
                                if (baseQingSongDaiModel.getCode() == 200 && baseQingSongDaiModel.getData() != null) {
                                    if (baseQingSongDaiModel.getData() != null && baseQingSongDaiModel.getData().size() > 0) {
                                        productQingSongDaiModel = baseQingSongDaiModel.getData().get(0);
                                        initBannerAdapter(baseQingSongDaiModel.getData());
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

    public void toWeb(ProductQingSongDaiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenQingSongDaiUtil.jumpPage(getActivity(), JumpH5QingSongDaiActivity.class, bundle);
        }
    }
}
