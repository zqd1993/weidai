package com.dlproject.jxdk.lejieqianbaof;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dlproject.jxdk.R;
import com.dlproject.jxdk.lejieqianbaoa.ImageAdapterLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaoa.JumpH5ActivityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaoapi.HttpApiLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.BaseModelLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.ProductModelLeJieQianBao;
import com.dlproject.jxdk.mvp.XFragment;
import com.dlproject.jxdk.net.ApiSubscriber;
import com.dlproject.jxdk.net.NetError;
import com.dlproject.jxdk.net.XApi;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoOpenUtil;
import com.dlproject.jxdk.lejieqianbaou.PreferencesOpenUtilLeJieQianBao;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragmentLeJieQianBao extends XFragment {

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
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.msg_layout)
    View msgLayout;
    @BindView(R.id.click_fl)
    View click_fl;

    private ProductModelLeJieQianBao productModelLeJieQianBao;

    private Bundle bundle;

    private ImageAdapterLeJieQianBao imageAdapterLeJieQianBao;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    @Override
    public void initData(Bundle savedInstanceState) {
//        msgLayout.setVisibility(View.VISIBLE);
        initViewData();
        setViewConfig();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelLeJieQianBao);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelLeJieQianBao);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelLeJieQianBao);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductModelLeJieQianBao> data) {
        imageAdapterLeJieQianBao = null;
        imageAdapterLeJieQianBao = new ImageAdapterLeJieQianBao(data);
        imageAdapterLeJieQianBao.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterLeJieQianBao);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_le_jie_qian_bao;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelLeJieQianBao model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilLeJieQianBao.getString("phone");
            HttpApiLeJieQianBao.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao baseModelLeJieQianBao) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencesOpenUtilLeJieQianBao.getInt("mobileType");
            phone = PreferencesOpenUtilLeJieQianBao.getString("phone");
            productModelLeJieQianBao = null;
            HttpApiLeJieQianBao.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            LeJieQianBaoOpenUtil.showErrorInfo(getActivity(), error);
                            if (imageAdapterLeJieQianBao == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>> baseModelLeJieQianBao) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelLeJieQianBao != null) {
                                if (baseModelLeJieQianBao.getCode() == 200 && baseModelLeJieQianBao.getData() != null) {
                                    if (baseModelLeJieQianBao.getData() != null && baseModelLeJieQianBao.getData().size() > 0) {
                                        productModelLeJieQianBao = baseModelLeJieQianBao.getData().get(0);
                                        initBannerAdapter(baseModelLeJieQianBao.getData());
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

    private void setViewConfig() {
        viewFlipper.setInAnimation(getActivity(), R.anim.text_anim_in);
        viewFlipper.setOutAnimation(getActivity(), R.anim.text_anim_out);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    private void initViewData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {
            datas.add(msg[i]);
        }
        for (String data : datas) {
            View view = getLayoutInflater().inflate(R.layout.view_flipper_le_jie_qian_bao, null);
            TextView textView = view.findViewById(R.id.msg_view);
            textView.setText(data);
            viewFlipper.addView(view);
        }
    }

    public void toWeb(ProductModelLeJieQianBao model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            LeJieQianBaoOpenUtil.jumpPage(getActivity(), JumpH5ActivityLeJieQianBao.class, bundle);
        }
    }
}
