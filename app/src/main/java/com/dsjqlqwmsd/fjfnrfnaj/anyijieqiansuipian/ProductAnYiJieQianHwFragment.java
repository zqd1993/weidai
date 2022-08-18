package com.dsjqlqwmsd.fjfnrfnaj.anyijieqiansuipian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dsjqlqwmsd.fjfnrfnaj.R;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian.JumpH5AnYiJieQianHwActivity;
import com.dsjqlqwmsd.fjfnrfnaj.mvp.XActivity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianjiekou.AnYiJieQianHwApi;
import com.dsjqlqwmsd.fjfnrfnaj.imageloader.ILFactory;
import com.dsjqlqwmsd.fjfnrfnaj.imageloader.ILoader;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.BaseAnYiJieQianHwModel;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.ProductAnYiJieQianHwModel;
import com.dsjqlqwmsd.fjfnrfnaj.mvp.XFragment;
import com.dsjqlqwmsd.fjfnrfnaj.net.ApiSubscriber;
import com.dsjqlqwmsd.fjfnrfnaj.net.NetError;
import com.dsjqlqwmsd.fjfnrfnaj.net.XApi;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.OpenAnYiJieQianHwUtil;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.PreferencesAnYiJieQianHwOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductAnYiJieQianHwFragment extends XFragment {

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
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.top_fl)
    View top_fl;
    private ProductAnYiJieQianHwModel productAnYiJieQianHwModel;

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
        initViewData();
        setViewConfig();
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productAnYiJieQianHwModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productAnYiJieQianHwModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productAnYiJieQianHwModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.an_yi_jie_qian_fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductAnYiJieQianHwModel model) {
        if (model != null) {
            phone = PreferencesAnYiJieQianHwOpenUtil.getString("phone");
            AnYiJieQianHwApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseAnYiJieQianHwModel baseModel) {
                            toWeb(model);
                        }
                    });
        }
    }


    public void productList() {
        mobileType = PreferencesAnYiJieQianHwOpenUtil.getInt("mobileType");
        AnYiJieQianHwApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel<List<ProductAnYiJieQianHwModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenAnYiJieQianHwUtil.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel<List<ProductAnYiJieQianHwModel>> baseModel) {
                        setRefreshing.setRefreshing(false);
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productAnYiJieQianHwModel = baseModel.getData().get(0);
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


    private void setViewConfig() {
        viewFlipper.setInAnimation(getActivity(), R.anim.jixin_anim_in);
        viewFlipper.setOutAnimation(getActivity(), R.anim.jixin_anim_out);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    private void initViewData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {
            datas.add(msg[i]);
        }
        for (String data : datas) {
            View view = getLayoutInflater().inflate(R.layout.an_yi_jie_qian_view_flipper, null);
            TextView textView = view.findViewById(R.id.msg_view);
            textView.setText(data);
            viewFlipper.addView(view);
        }
    }

    private void addProductView(List<ProductAnYiJieQianHwModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductAnYiJieQianHwModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.an_yi_jie_qian_layout_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            TextView zhouqi_tv = view.findViewById(R.id.zhouqi_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            ILFactory.getLoader().loadNet(pic, AnYiJieQianHwApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductAnYiJieQianHwModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), JumpH5AnYiJieQianHwActivity.class, bundle);
        }
    }
}
