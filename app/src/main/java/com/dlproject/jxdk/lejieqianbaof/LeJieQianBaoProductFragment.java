package com.dlproject.jxdk.lejieqianbaof;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dlproject.jxdk.R;
import com.dlproject.jxdk.lejieqianbaoa.JumpH5ActivityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaoapi.HttpApiLeJieQianBao;
import com.dlproject.jxdk.imageloader.ILFactory;
import com.dlproject.jxdk.imageloader.ILoader;
import com.dlproject.jxdk.lejieqianbaom.BaseModelLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.ProductModelLeJieQianBao;
import com.dlproject.jxdk.mvp.XActivity;
import com.dlproject.jxdk.mvp.XFragment;
import com.dlproject.jxdk.net.ApiSubscriber;
import com.dlproject.jxdk.net.NetError;
import com.dlproject.jxdk.net.XApi;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoOpenUtil;
import com.dlproject.jxdk.lejieqianbaou.PreferencesOpenUtilLeJieQianBao;

import java.util.List;

import butterknife.BindView;

public class LeJieQianBaoProductFragment extends XFragment {

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
    @BindView(R.id.click_fl)
    View click_fl;
    private ProductModelLeJieQianBao productModelLeJieQianBao;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
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
        goodsListLl.setOnClickListener(v -> {
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_le_jie_qian_bao;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelLeJieQianBao model) {
            if (model != null) {
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
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>> baseModelLeJieQianBao) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (baseModelLeJieQianBao != null) {
                                if (baseModelLeJieQianBao.getCode() == 200 && baseModelLeJieQianBao.getData() != null) {
                                    if (baseModelLeJieQianBao.getData() != null && baseModelLeJieQianBao.getData().size() > 0) {
                                        productModelLeJieQianBao = baseModelLeJieQianBao.getData().get(0);
                                        addProductView(baseModelLeJieQianBao.getData());
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

    private void addProductView(List<ProductModelLeJieQianBao> mList) {
        for (ProductModelLeJieQianBao model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_le_jie_qian_bao_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            ILFactory.getLoader().loadNet(pic, HttpApiLeJieQianBao.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            shijian_tv.setText("周期" + model.getDes() + "个月");
            shuliang_tv.setText(model.getPassingRate() + "下款");
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

    public void toWeb(ProductModelLeJieQianBao model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            LeJieQianBaoOpenUtil.jumpPage((XActivity) getActivity(), JumpH5ActivityLeJieQianBao.class, bundle);
        }
    }
}
