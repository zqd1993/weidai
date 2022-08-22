package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zmansdjhsdn.vpcxkassna.R;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.ImageAdapterTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.JumpTiQianHuaDaikuanHwH5Activity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwapi.HttpTiQianHuaDaikuanHwApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.BaseTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ProductModelTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.mvp.XFragment;
import com.zmansdjhsdn.vpcxkassna.net.ApiSubscriber;
import com.zmansdjhsdn.vpcxkassna.net.NetError;
import com.zmansdjhsdn.vpcxkassna.net.XApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.OpenTiQianHuaDaikuanHwUtil;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.PreferencesOpenUtilTiQianHuaDaikuanHw;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainTiQianHuaDaikuanHwFragment extends XFragment {

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

    private ProductModelTiQianHuaDaikuanHw productModelTiQianHuaDaikuanHw;

    private Bundle bundle;

    private ImageAdapterTiQianHuaDaikuanHw imageAdapterTiQianHuaDaikuanHw;

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
            productClick(productModelTiQianHuaDaikuanHw);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelTiQianHuaDaikuanHw);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductModelTiQianHuaDaikuanHw> data) {
        imageAdapterTiQianHuaDaikuanHw = null;
        imageAdapterTiQianHuaDaikuanHw = new ImageAdapterTiQianHuaDaikuanHw(data);
        imageAdapterTiQianHuaDaikuanHw.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterTiQianHuaDaikuanHw);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ti_qian_hua_dai_kuan_hw_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelTiQianHuaDaikuanHw model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("phone");
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel baseTiQianHuaDaikuanHwModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencesOpenUtilTiQianHuaDaikuanHw.getInt("mobileType");
            phone = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("phone");
            productModelTiQianHuaDaikuanHw = null;
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel<List<ProductModelTiQianHuaDaikuanHw>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenTiQianHuaDaikuanHwUtil.showErrorInfo(getActivity(), error);
                            if (imageAdapterTiQianHuaDaikuanHw == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel<List<ProductModelTiQianHuaDaikuanHw>> baseTiQianHuaDaikuanHwModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseTiQianHuaDaikuanHwModel != null) {
                                if (baseTiQianHuaDaikuanHwModel.getCode() == 200 && baseTiQianHuaDaikuanHwModel.getData() != null) {
                                    if (baseTiQianHuaDaikuanHwModel.getData() != null && baseTiQianHuaDaikuanHwModel.getData().size() > 0) {
                                        productModelTiQianHuaDaikuanHw = baseTiQianHuaDaikuanHwModel.getData().get(0);
                                        initBannerAdapter(baseTiQianHuaDaikuanHwModel.getData());
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

    public void toWeb(ProductModelTiQianHuaDaikuanHw model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), JumpTiQianHuaDaikuanHwH5Activity.class, bundle);
        }
    }
}
