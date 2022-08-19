package com.linghuojiehuanopwesdf.dsfwethdfgwe.flinghuojiekuan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.ImageLingHuoJieHuanAdapter;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.JumpH5LingHuoJieHuanActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan.HttpLingHuoJieHuanApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.BaseModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ProductLingHuoJieHuanModel;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mvp.XFragment;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.ApiSubscriber;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.NetError;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.XApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanOpenUtil;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.PreferencesOpenUtilLingHuoJieHuan;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFragmentLingHuoJieHuan extends XFragment {

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

    private ProductLingHuoJieHuanModel productLingHuoJieHuanModel;

    private Bundle bundle;

    private ImageLingHuoJieHuanAdapter imageLingHuoJieHuanAdapter;

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
            productClick(productLingHuoJieHuanModel);
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

    private void initBannerAdapter(List<ProductLingHuoJieHuanModel> data) {
        imageLingHuoJieHuanAdapter = null;
        imageLingHuoJieHuanAdapter = new ImageLingHuoJieHuanAdapter(data);
        imageLingHuoJieHuanAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageLingHuoJieHuanAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_ling_huo_jie_huan;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductLingHuoJieHuanModel model) {
            if (model == null) {
                return;
            }
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
                            if (imageLingHuoJieHuanAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan<List<ProductLingHuoJieHuanModel>> baseModelLingHuoJieHuan) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelLingHuoJieHuan != null) {
                                if (baseModelLingHuoJieHuan.getCode() == 200 && baseModelLingHuoJieHuan.getData() != null) {
                                    if (baseModelLingHuoJieHuan.getData() != null && baseModelLingHuoJieHuan.getData().size() > 0) {
                                        productLingHuoJieHuanModel = baseModelLingHuoJieHuan.getData().get(0);
                                        initBannerAdapter(baseModelLingHuoJieHuan.getData());
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

    public void toWeb(ProductLingHuoJieHuanModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            LingHuoJieHuanOpenUtil.jumpPage(getActivity(), JumpH5LingHuoJieHuanActivity.class, bundle);
        }
    }
}
