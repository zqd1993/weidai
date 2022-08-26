package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvof;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.JumpKuaiJieJieKuanNewVoH5Activity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoapi.HttpKuaiJieJieKuanNewVoApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.imageloader.ILFactory;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.imageloader.ILoader;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.BaseKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ProductModelKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.mvp.XFragment;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.ApiSubscriber;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.NetError;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.XApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.OpenKuaiJieJieKuanNewVoUtil;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.PreferencesOpenUtilKuaiJieJieKuanNewVo;

import java.util.List;

import butterknife.BindView;

public class ProductKuaiJieJieKuanNewVoFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.click_fl)
    View click_fl;
    private ProductModelKuaiJieJieKuanNewVo productModelKuaiJieJieKuanNewVo;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelKuaiJieJieKuanNewVo);
        });
        goodsListLl.setOnClickListener(v -> {
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_kuai_jie_jie_kuan_new_op;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelKuaiJieJieKuanNewVo model) {
            if (model != null) {
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
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel<List<ProductModelKuaiJieJieKuanNewVo>> baseKuaiJieJieKuanNewVoModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (baseKuaiJieJieKuanNewVoModel != null) {
                                if (baseKuaiJieJieKuanNewVoModel.getCode() == 200 && baseKuaiJieJieKuanNewVoModel.getData() != null) {
                                    if (baseKuaiJieJieKuanNewVoModel.getData() != null && baseKuaiJieJieKuanNewVoModel.getData().size() > 0) {
                                        productModelKuaiJieJieKuanNewVo = baseKuaiJieJieKuanNewVoModel.getData().get(0);
                                        addProductView(baseKuaiJieJieKuanNewVoModel.getData());
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

    private void addProductView(List<ProductModelKuaiJieJieKuanNewVo> mList) {
        for (int i = 0; i < mList.size(); i++) {
            ProductModelKuaiJieJieKuanNewVo model = mList.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_kuai_jie_jie_kuan_new_op_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            ImageView pic1 = view.findViewById(R.id.product_img_1);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpKuaiJieJieKuanNewVoApi.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            ILFactory.getLoader().loadNet(pic1, HttpKuaiJieJieKuanNewVoApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            if (i % 2 == 0){
                pic.setVisibility(View.GONE);
                pic1.setVisibility(View.VISIBLE);
            } else {
                pic.setVisibility(View.VISIBLE);
                pic1.setVisibility(View.GONE);
            }
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            pic1.setOnClickListener(v -> {
                productClick(model);
            });
            yjsqSl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    public void toWeb(ProductModelKuaiJieJieKuanNewVo model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), JumpKuaiJieJieKuanNewVoH5Activity.class, bundle);
        }
    }
}
