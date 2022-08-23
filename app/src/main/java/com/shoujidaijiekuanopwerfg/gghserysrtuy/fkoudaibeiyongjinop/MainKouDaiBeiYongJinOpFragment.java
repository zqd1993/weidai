package com.shoujidaijiekuanopwerfg.gghserysrtuy.fkoudaibeiyongjinop;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.R;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.ImageAdapterKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.JumpKouDaiBeiYongJinOpH5Activity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.apikoudaibeiyongjinop.HttpKouDaiBeiYongJinOpApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.BaseKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ProductModelKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mvp.XFragment;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.ApiSubscriber;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.NetError;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.XApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.OpenKouDaiBeiYongJinOpUtil;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.PreferencesOpenUtilKouDaiBeiYongJinOp;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainKouDaiBeiYongJinOpFragment extends XFragment {

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

    private ProductModelKouDaiBeiYongJinOp productModelKouDaiBeiYongJinOp;

    private Bundle bundle;

    private ImageAdapterKouDaiBeiYongJinOp imageAdapterKouDaiBeiYongJinOp;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelKouDaiBeiYongJinOp);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelKouDaiBeiYongJinOp);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductModelKouDaiBeiYongJinOp> data) {
        imageAdapterKouDaiBeiYongJinOp = null;
        imageAdapterKouDaiBeiYongJinOp = new ImageAdapterKouDaiBeiYongJinOp(data);
        imageAdapterKouDaiBeiYongJinOp.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterKouDaiBeiYongJinOp);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kou_dai_bei_yong_jin_op_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelKouDaiBeiYongJinOp model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilKouDaiBeiYongJinOp.getString("phone");
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel baseKouDaiBeiYongJinOpModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencesOpenUtilKouDaiBeiYongJinOp.getInt("mobileType");
            phone = PreferencesOpenUtilKouDaiBeiYongJinOp.getString("phone");
            productModelKouDaiBeiYongJinOp = null;
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel<List<ProductModelKouDaiBeiYongJinOp>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenKouDaiBeiYongJinOpUtil.showErrorInfo(getActivity(), error);
                            if (imageAdapterKouDaiBeiYongJinOp == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel<List<ProductModelKouDaiBeiYongJinOp>> baseKouDaiBeiYongJinOpModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseKouDaiBeiYongJinOpModel != null) {
                                if (baseKouDaiBeiYongJinOpModel.getCode() == 200 && baseKouDaiBeiYongJinOpModel.getData() != null) {
                                    if (baseKouDaiBeiYongJinOpModel.getData() != null && baseKouDaiBeiYongJinOpModel.getData().size() > 0) {
                                        productModelKouDaiBeiYongJinOp = baseKouDaiBeiYongJinOpModel.getData().get(0);
                                        initBannerAdapter(baseKouDaiBeiYongJinOpModel.getData());
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

    public void toWeb(ProductModelKouDaiBeiYongJinOp model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), JumpKouDaiBeiYongJinOpH5Activity.class, bundle);
        }
    }
}
