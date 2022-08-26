package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvof;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.ImageAdapterKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.JumpKuaiJieJieKuanNewVoH5Activity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoapi.HttpKuaiJieJieKuanNewVoApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.BaseKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ProductModelKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.mvp.XFragment;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.ApiSubscriber;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.NetError;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.XApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.OpenKuaiJieJieKuanNewVoUtil;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.PreferencesOpenUtilKuaiJieJieKuanNewVo;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainKuaiJieJieKuanNewVoFragment extends XFragment {

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

    private ProductModelKuaiJieJieKuanNewVo productModelKuaiJieJieKuanNewVo;

    private Bundle bundle;

    private ImageAdapterKuaiJieJieKuanNewVo imageAdapterKouDaiBeiYongJinOp;

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
            productClick(productModelKuaiJieJieKuanNewVo);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelKuaiJieJieKuanNewVo);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductModelKuaiJieJieKuanNewVo> data) {
        imageAdapterKouDaiBeiYongJinOp = null;
        imageAdapterKouDaiBeiYongJinOp = new ImageAdapterKuaiJieJieKuanNewVo(data);
        imageAdapterKouDaiBeiYongJinOp.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterKouDaiBeiYongJinOp);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kuai_jie_jie_kuan_new_op_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelKuaiJieJieKuanNewVo model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilKuaiJieJieKuanNewVo.getString("phone");
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel baseKuaiJieJieKuanNewVoModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencesOpenUtilKuaiJieJieKuanNewVo.getInt("mobileType");
            phone = PreferencesOpenUtilKuaiJieJieKuanNewVo.getString("phone");
            productModelKuaiJieJieKuanNewVo = null;
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel<List<ProductModelKuaiJieJieKuanNewVo>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenKuaiJieJieKuanNewVoUtil.showErrorInfo(getActivity(), error);
                            if (imageAdapterKouDaiBeiYongJinOp == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel<List<ProductModelKuaiJieJieKuanNewVo>> baseKuaiJieJieKuanNewVoModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseKuaiJieJieKuanNewVoModel != null) {
                                if (baseKuaiJieJieKuanNewVoModel.getCode() == 200 && baseKuaiJieJieKuanNewVoModel.getData() != null) {
                                    if (baseKuaiJieJieKuanNewVoModel.getData() != null && baseKuaiJieJieKuanNewVoModel.getData().size() > 0) {
                                        productModelKuaiJieJieKuanNewVo = baseKuaiJieJieKuanNewVoModel.getData().get(0);
                                        initBannerAdapter(baseKuaiJieJieKuanNewVoModel.getData());
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

    public void toWeb(ProductModelKuaiJieJieKuanNewVo model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), JumpKuaiJieJieKuanNewVoH5Activity.class, bundle);
        }
    }
}
