package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.ImageAdapterLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.JumpH5ActivityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi.HttpApiLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.BaseModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ProductModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.mvp.XFragment;
import com.linghuojieasdufne.vbdsetrrte.net.ApiSubscriber;
import com.linghuojieasdufne.vbdsetrrte.net.NetError;
import com.linghuojieasdufne.vbdsetrrte.net.XApi;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdOpenUtil;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.PreferencesOpenUtilLingHuoJieSvsdFd;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFragmentLingHuoJieSvsdFd extends XFragment {

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

    private ProductModelLingHuoJieSvsdFd productModelLingHuoJieSvsdFd;

    private Bundle bundle;

    private ImageAdapterLingHuoJieSvsdFd imageAdapterLingHuoJieSvsdFd;

    @Override
    public void initData(Bundle savedInstanceState) {
//        msgLayout.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelLingHuoJieSvsdFd);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductModelLingHuoJieSvsdFd> data) {
        imageAdapterLingHuoJieSvsdFd = null;
        imageAdapterLingHuoJieSvsdFd = new ImageAdapterLingHuoJieSvsdFd(data);
        imageAdapterLingHuoJieSvsdFd.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterLingHuoJieSvsdFd);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_ling_huo_jie_djs_urng;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelLingHuoJieSvsdFd model) {
            if (model == null) {
                return;
            }
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
                            if (imageAdapterLingHuoJieSvsdFd == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd<List<ProductModelLingHuoJieSvsdFd>> baseModelLingHuoJieSvsdFd) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelLingHuoJieSvsdFd != null) {
                                if (baseModelLingHuoJieSvsdFd.getCode() == 200 && baseModelLingHuoJieSvsdFd.getData() != null) {
                                    if (baseModelLingHuoJieSvsdFd.getData() != null && baseModelLingHuoJieSvsdFd.getData().size() > 0) {
                                        productModelLingHuoJieSvsdFd = baseModelLingHuoJieSvsdFd.getData().get(0);
                                        initBannerAdapter(baseModelLingHuoJieSvsdFd.getData());
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

    public void toWeb(ProductModelLingHuoJieSvsdFd model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), JumpH5ActivityLingHuoJieSvsdFd.class, bundle);
        }
    }
}
