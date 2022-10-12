package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyusuipian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.snvhretnsjggdsfd.tughuturtgf.R;
import com.snvhretnsjggdsfd.tughuturtgf.mvp.XActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.JumpH5HaoJJQiaHwfhsnActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuapi.HaoJJQiaHwfhsnApi;
import com.snvhretnsjggdsfd.tughuturtgf.imageloader.ILFactory;
import com.snvhretnsjggdsfd.tughuturtgf.imageloader.ILoader;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.BaseHaoJJQiaHwfhsnModel;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.ProductHaoJJQiaHwfhsnModel;
import com.snvhretnsjggdsfd.tughuturtgf.mvp.XFragment;
import com.snvhretnsjggdsfd.tughuturtgf.net.ApiSubscriber;
import com.snvhretnsjggdsfd.tughuturtgf.net.NetError;
import com.snvhretnsjggdsfd.tughuturtgf.net.XApi;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.OpenHaoJJQiaHwfhsnUtil;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.PreferenceHaoJJQiaHwfhsnOpenUtil;

import java.util.List;

import butterknife.BindView;

public class ProductHaoJJQiaHwfhsnFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.shenqing_sl)
    View shenqing_sl;
    @BindView(R.id.more_fl)
    View moreFl;
    @BindView(R.id.top_fl)
    View top_fl;
    private ProductHaoJJQiaHwfhsnModel productHaoJJQiaHwfhsnModel;

    private Bundle bundle;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        shenqing_sl.setVisibility(View.GONE);
        moreFl.setVisibility(View.GONE);
        top_fl.setVisibility(View.GONE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productHaoJJQiaHwfhsnModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productHaoJJQiaHwfhsnModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productHaoJJQiaHwfhsnModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.hao_jjqian_dfjr_uert_hw_fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductHaoJJQiaHwfhsnModel model) {
        if (model != null) {
            phone = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("phone");
            HaoJJQiaHwfhsnApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseHaoJJQiaHwfhsnModel baseModel) {
                            toWeb(model);
                        }
                    });
        }
    }


    public void productList() {
        mobileType = PreferenceHaoJJQiaHwfhsnOpenUtil.getInt("mobileType");
        phone = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("phone");
        HaoJJQiaHwfhsnApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel<List<ProductHaoJJQiaHwfhsnModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenHaoJJQiaHwfhsnUtil.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseHaoJJQiaHwfhsnModel<List<ProductHaoJJQiaHwfhsnModel>> baseModel) {
                        setRefreshing.setRefreshing(false);
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productHaoJJQiaHwfhsnModel = baseModel.getData().get(0);
                                    addProductView(baseModel.getData());
                                } else {
                                    if (goodsListLl.getChildCount() == 0) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (goodsListLl.getChildCount() == 0) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }


    private void addProductView(List<ProductHaoJJQiaHwfhsnModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductHaoJJQiaHwfhsnModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.hao_jjqian_dfjr_uert_hw_layout_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            TextView zhouqi_tv = view.findViewById(R.id.zhouqi_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            ILFactory.getLoader().loadNet(pic, HaoJJQiaHwfhsnApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            zhouqi_tv.setText(model.getDes() + "个月");
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

    public void toWeb(ProductHaoJJQiaHwfhsnModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), JumpH5HaoJJQiaHwfhsnActivity.class, bundle);
        }
    }
}