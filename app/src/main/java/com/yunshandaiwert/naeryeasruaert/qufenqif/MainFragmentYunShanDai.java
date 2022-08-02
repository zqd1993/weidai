package com.yunshandaiwert.naeryeasruaert.qufenqif;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yunshandaiwert.naeryeasruaert.R;
import com.yunshandaiwert.naeryeasruaert.qufenqia.ImageYunShanDaiAdapter;
import com.yunshandaiwert.naeryeasruaert.qufenqia.JumpYunShanDaiH5Activity;
import com.yunshandaiwert.naeryeasruaert.qufenqia.YunShanDaiMainActivity;
import com.yunshandaiwert.naeryeasruaert.qufenqiapi.HttpYunShanDaiApi;
import com.yunshandaiwert.naeryeasruaert.imageloader.ILFactory;
import com.yunshandaiwert.naeryeasruaert.imageloader.ILoader;
import com.yunshandaiwert.naeryeasruaert.qufenqim.BannerYunShanDaiModel;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiBaseModel;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiProductModel;
import com.yunshandaiwert.naeryeasruaert.mvp.XActivity;
import com.yunshandaiwert.naeryeasruaert.mvp.XFragment;
import com.yunshandaiwert.naeryeasruaert.net.ApiSubscriber;
import com.yunshandaiwert.naeryeasruaert.net.NetError;
import com.yunshandaiwert.naeryeasruaert.net.XApi;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiOpenUtil;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiPreferencesOpenUtil;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFragmentYunShanDai extends XFragment {

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
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.banner_img)
    ImageView banner_img;
    @BindView(R.id.mine_tv)
    TextView mine_tv;

    private YunShanDaiProductModel yunShanDaiProductModel;

    private Bundle bundle;

    private ImageYunShanDaiAdapter imageYunShanDaiAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
//        banner_fl.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(yunShanDaiProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(yunShanDaiProductModel);
        });
        mine_tv.setOnClickListener(v -> {
            ((YunShanDaiMainActivity) getActivity()).jumpMine();
        });
        banner_img.setOnClickListener(v -> {
            productClick(yunShanDaiProductModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    private void initBannerAdapter(List<YunShanDaiProductModel> data) {
        imageYunShanDaiAdapter = null;
        imageYunShanDaiAdapter = new ImageYunShanDaiAdapter(data);
        imageYunShanDaiAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageYunShanDaiAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_yun_shan_dai_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(YunShanDaiProductModel model) {
        if (model == null) {
            return;
        }
        phone = YunShanDaiPreferencesOpenUtil.getString("phone");
        HttpYunShanDaiApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<YunShanDaiBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(YunShanDaiBaseModel yunShanDaiBaseModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = YunShanDaiPreferencesOpenUtil.getInt("mobileType");
        phone = YunShanDaiPreferencesOpenUtil.getString("phone");
        yunShanDaiProductModel = null;
        HttpYunShanDaiApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<YunShanDaiBaseModel<List<YunShanDaiProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        YunShanDaiOpenUtil.showErrorInfo(getActivity(), error);
                        if (imageYunShanDaiAdapter == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(YunShanDaiBaseModel<List<YunShanDaiProductModel>> yunShanDaiBaseModel) {
                        setRefreshing.setRefreshing(false);
                        goodsListLl.removeAllViews();
                        if (yunShanDaiBaseModel != null) {
                            if (yunShanDaiBaseModel.getCode() == 200 && yunShanDaiBaseModel.getData() != null) {
                                if (yunShanDaiBaseModel.getData() != null && yunShanDaiBaseModel.getData().size() > 0) {
                                    yunShanDaiProductModel = yunShanDaiBaseModel.getData().get(0);
//                                        initBannerAdapter(baseModel.getData());
                                    addProductView(yunShanDaiBaseModel.getData());
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
        HttpYunShanDaiApi.getInterfaceUtils().bannerList()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<YunShanDaiBaseModel<List<BannerYunShanDaiModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        YunShanDaiOpenUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(YunShanDaiBaseModel<List<BannerYunShanDaiModel>> yunShanDaiBaseModel) {
                        if (yunShanDaiBaseModel != null) {
                            if (yunShanDaiBaseModel.getCode() == 200) {
                                if (yunShanDaiBaseModel.getData() != null && yunShanDaiBaseModel.getData().size() > 0) {
                                    ILFactory.getLoader().loadNet(banner_img, HttpYunShanDaiApi.HTTP_API_URL + yunShanDaiBaseModel.getData().get(0).getLogo(),
                                            new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                }
                            }
                        }
                    }
                });
    }

    private void addProductView(List<YunShanDaiProductModel> mList) {
        for (YunShanDaiProductModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_yun_shan_dai, null);
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
                ILFactory.getLoader().loadNet(pic, HttpYunShanDaiApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(YunShanDaiProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            YunShanDaiOpenUtil.getValue((XActivity) getActivity(), JumpYunShanDaiH5Activity.class, bundle);
        }
    }
}
