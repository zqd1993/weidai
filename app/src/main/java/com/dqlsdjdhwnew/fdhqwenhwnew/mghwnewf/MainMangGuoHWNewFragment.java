package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.ImageMangGuoHWNewAdapter;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.JumpH5MangGuoHWNewActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewapi.HttpApiMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewBaseModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.ProductModelMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mvp.XFragment;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.ApiSubscriber;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.NetError;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.XApi;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MangGuoHWNewOpenUtil;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainMangGuoHWNewFragment extends XFragment {

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
    @BindView(R.id.parent_ll)
    View parent_ll;

    private ProductModelMangGuoHWNew productModelMangGuoHWNew;

    private Bundle bundle;

    private ImageMangGuoHWNewAdapter imageMangGuoHWNewAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelMangGuoHWNew);
        });
        parent_ll.setOnClickListener(v -> {
            productClick(productModelMangGuoHWNew);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelMangGuoHWNew);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductModelMangGuoHWNew> data) {
        imageMangGuoHWNewAdapter = null;
        imageMangGuoHWNewAdapter = new ImageMangGuoHWNewAdapter(data);
        imageMangGuoHWNewAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageMangGuoHWNewAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_mang_guo_hw_new;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelMangGuoHWNew model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilMangGuoHWNew.getString("phone");
            HttpApiMangGuoHWNew.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel mangGuoHWNewBaseModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencesOpenUtilMangGuoHWNew.getInt("mobileType");
            phone = PreferencesOpenUtilMangGuoHWNew.getString("phone");
            productModelMangGuoHWNew = null;
            HttpApiMangGuoHWNew.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel<List<ProductModelMangGuoHWNew>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            MangGuoHWNewOpenUtil.showErrorInfo(getActivity(), error);
                            if (imageMangGuoHWNewAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel<List<ProductModelMangGuoHWNew>> mangGuoHWNewBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (mangGuoHWNewBaseModel != null) {
                                if (mangGuoHWNewBaseModel.getCode() == 200 && mangGuoHWNewBaseModel.getData() != null) {
                                    if (mangGuoHWNewBaseModel.getData() != null && mangGuoHWNewBaseModel.getData().size() > 0) {
                                        productModelMangGuoHWNew = mangGuoHWNewBaseModel.getData().get(0);
                                        initBannerAdapter(mangGuoHWNewBaseModel.getData());
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

    public void toWeb(ProductModelMangGuoHWNew model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            MangGuoHWNewOpenUtil.jumpPage(getActivity(), JumpH5MangGuoHWNewActivity.class, bundle);
        }
    }
}
