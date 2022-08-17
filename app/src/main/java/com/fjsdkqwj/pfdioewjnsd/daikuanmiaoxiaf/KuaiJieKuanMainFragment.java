package com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.ImageKuaiJieKuanAdapter;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.JumpH5ActivityKuaiJieKuan;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.KuaiJieKuanBaseModel;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.ProductKuaiJieKuanModel;
import com.fjsdkqwj.pfdioewjnsd.mvp.XFragment;
import com.fjsdkqwj.pfdioewjnsd.net.ApiSubscriber;
import com.fjsdkqwj.pfdioewjnsd.net.NetError;
import com.fjsdkqwj.pfdioewjnsd.net.XApi;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau.OpenKuaiJieKuanUtil;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau.PreferencesOpenUtilKuaiJieKuan;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class KuaiJieKuanMainFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.click_fl)
    View click_fl;

    private ProductKuaiJieKuanModel productKuaiJieKuanModel;

    private Bundle bundle;

    private ImageKuaiJieKuanAdapter imageKuaiJieKuanAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
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

    private void initBannerAdapter(List<ProductKuaiJieKuanModel> data) {
        imageKuaiJieKuanAdapter = null;
        imageKuaiJieKuanAdapter = new ImageKuaiJieKuanAdapter(data);
        imageKuaiJieKuanAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageKuaiJieKuanAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment__kuai_jie_kuan_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductKuaiJieKuanModel model) {
            if (model == null) {
                return;
            }
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
                            if (imageKuaiJieKuanAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel<List<ProductKuaiJieKuanModel>> kuaiJieKuanBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (kuaiJieKuanBaseModel != null) {
                                if (kuaiJieKuanBaseModel.getCode() == 200 && kuaiJieKuanBaseModel.getData() != null) {
                                    if (kuaiJieKuanBaseModel.getData() != null && kuaiJieKuanBaseModel.getData().size() > 0) {
                                        productKuaiJieKuanModel = kuaiJieKuanBaseModel.getData().get(0);
                                        initBannerAdapter(kuaiJieKuanBaseModel.getData());
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

    public void toWeb(ProductKuaiJieKuanModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenKuaiJieKuanUtil.jumpPage(getActivity(), JumpH5ActivityKuaiJieKuan.class, bundle);
        }
    }
}
