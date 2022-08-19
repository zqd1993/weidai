package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviof;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.R;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.ImageFenQiXiangVioAdapter;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.JumpH5FenQiXiangVioActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioBaseModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.ProductModelFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.mvp.XFragment;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.ApiSubscriber;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.NetError;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.XApi;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.FenQiXiangVioOpenUtil;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFenQiXiangVioFragment extends XFragment {

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

    private ProductModelFenQiXiangVio productModelFenQiXiangVio;

    private Bundle bundle;

    private ImageFenQiXiangVioAdapter imageFenQiXiangVioAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelFenQiXiangVio);
        });
        parent_ll.setOnClickListener(v -> {
            productClick(productModelFenQiXiangVio);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelFenQiXiangVio);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    private void initBannerAdapter(List<ProductModelFenQiXiangVio> data) {
        imageFenQiXiangVioAdapter = null;
        imageFenQiXiangVioAdapter = new ImageFenQiXiangVioAdapter(data);
        imageFenQiXiangVioAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageFenQiXiangVioAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_fen_xiang_qi_vio;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelFenQiXiangVio model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
            HttpApiFenQiXiangVio.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel fenQiXiangVioBaseModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencesOpenUtilFenQiXiangVio.getInt("mobileType");
            phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
            productModelFenQiXiangVio = null;
            HttpApiFenQiXiangVio.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<List<ProductModelFenQiXiangVio>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            FenQiXiangVioOpenUtil.showErrorInfo(getActivity(), error);
                            if (imageFenQiXiangVioAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel<List<ProductModelFenQiXiangVio>> fenQiXiangVioBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (fenQiXiangVioBaseModel != null) {
                                if (fenQiXiangVioBaseModel.getCode() == 200 && fenQiXiangVioBaseModel.getData() != null) {
                                    if (fenQiXiangVioBaseModel.getData() != null && fenQiXiangVioBaseModel.getData().size() > 0) {
                                        productModelFenQiXiangVio = fenQiXiangVioBaseModel.getData().get(0);
                                        initBannerAdapter(fenQiXiangVioBaseModel.getData());
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

    public void toWeb(ProductModelFenQiXiangVio model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            FenQiXiangVioOpenUtil.jumpPage(getActivity(), JumpH5FenQiXiangVioActivity.class, bundle);
        }
    }
}
