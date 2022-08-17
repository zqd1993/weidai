package com.lejieqianbaosdfwer.dfgseryaer.flejieqianbao;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lejieqianbaosdfwer.dfgseryaer.R;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.ImageAdapterLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.JumpH5ActivityLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.apilejieqianbao.HttpApiLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.BaseModelLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.ProductModelLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mvp.XFragment;
import com.lejieqianbaosdfwer.dfgseryaer.net.ApiSubscriber;
import com.lejieqianbaosdfwer.dfgseryaer.net.NetError;
import com.lejieqianbaosdfwer.dfgseryaer.net.XApi;
import com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao.LeJieQianBaoOpenUtil;
import com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao.PreferencesOpenUtilLeJieQianBao;
import com.youth.banner.Banner;

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
    @BindView(R.id.goods_banner)
    Banner banner;

    private ProductModelLeJieQianBao productModelLeJieQianBao;

    private Bundle bundle;

    private ImageAdapterLeJieQianBao imageAdapterLeJieQianBao;

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
            productClick(productModelLeJieQianBao);
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

    public void toWeb(ProductModelLeJieQianBao model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            LeJieQianBaoOpenUtil.jumpPage(getActivity(), JumpH5ActivityLeJieQianBao.class, bundle);
        }
    }
}
