package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.ImageDaiKuanMiaoXiaAdapter;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.JumpH5ActivityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi.DaiKuanMiaoXiaHttpApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DaiKuanMiaoXiaBaseModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ProductDaiKuanMiaoXiaModel;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XFragment;
import com.sdyqwjqwias.fdpwejqwdjew.net.ApiSubscriber;
import com.sdyqwjqwias.fdpwejqwdjew.net.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.OpenDaiKuanMiaoXiaUtil;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.PreferencesOpenUtilDaiKuanMiaoXia;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class DaiKuanMiaoXiaMainFragment extends XFragment {

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

    private ProductDaiKuanMiaoXiaModel productDaiKuanMiaoXiaModel;

    private Bundle bundle;

    private ImageDaiKuanMiaoXiaAdapter imageDaiKuanMiaoXiaAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
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

    private void initBannerAdapter(List<ProductDaiKuanMiaoXiaModel> data) {
        imageDaiKuanMiaoXiaAdapter = null;
        imageDaiKuanMiaoXiaAdapter = new ImageDaiKuanMiaoXiaAdapter(data);
        imageDaiKuanMiaoXiaAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageDaiKuanMiaoXiaAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dai_kuan_miao_xia_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductDaiKuanMiaoXiaModel model) {
            if (model == null) {
                return;
            }
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
                            if (imageDaiKuanMiaoXiaAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel<List<ProductDaiKuanMiaoXiaModel>> daiKuanMiaoXiaBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (daiKuanMiaoXiaBaseModel != null) {
                                if (daiKuanMiaoXiaBaseModel.getCode() == 200 && daiKuanMiaoXiaBaseModel.getData() != null) {
                                    if (daiKuanMiaoXiaBaseModel.getData() != null && daiKuanMiaoXiaBaseModel.getData().size() > 0) {
                                        productDaiKuanMiaoXiaModel = daiKuanMiaoXiaBaseModel.getData().get(0);
                                        initBannerAdapter(daiKuanMiaoXiaBaseModel.getData());
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

    public void toWeb(ProductDaiKuanMiaoXiaModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), JumpH5ActivityDaiKuanMiaoXia.class, bundle);
        }
    }
}
