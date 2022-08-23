package com.shoujidaijiekuanopwerfg.gghserysrtuy.fkoudaibeiyongjinop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.R;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.JumpKouDaiBeiYongJinOpH5Activity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.apikoudaibeiyongjinop.HttpKouDaiBeiYongJinOpApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.imageloader.ILFactory;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.imageloader.ILoader;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.BaseKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ProductModelKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mvp.XFragment;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.ApiSubscriber;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.NetError;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.XApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.OpenKouDaiBeiYongJinOpUtil;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.PreferencesOpenUtilKouDaiBeiYongJinOp;

import java.util.List;

import butterknife.BindView;

public class ProductKouDaiBeiYongJinOpFragment extends XFragment {

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
    private ProductModelKouDaiBeiYongJinOp productModelKouDaiBeiYongJinOp;

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
            productClick(productModelKouDaiBeiYongJinOp);
        });
        goodsListLl.setOnClickListener(v -> {
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_kou_dai_bei_yong_jin_op;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelKouDaiBeiYongJinOp model) {
            if (model != null) {
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
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel<List<ProductModelKouDaiBeiYongJinOp>> baseKouDaiBeiYongJinOpModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (baseKouDaiBeiYongJinOpModel != null) {
                                if (baseKouDaiBeiYongJinOpModel.getCode() == 200 && baseKouDaiBeiYongJinOpModel.getData() != null) {
                                    if (baseKouDaiBeiYongJinOpModel.getData() != null && baseKouDaiBeiYongJinOpModel.getData().size() > 0) {
                                        productModelKouDaiBeiYongJinOp = baseKouDaiBeiYongJinOpModel.getData().get(0);
                                        addProductView(baseKouDaiBeiYongJinOpModel.getData());
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

    private void addProductView(List<ProductModelKouDaiBeiYongJinOp> mList) {
        for (int i = 0; i < mList.size(); i++) {
            ProductModelKouDaiBeiYongJinOp model = mList.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_kou_dai_bei_yong_jin_op_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            ImageView pic1 = view.findViewById(R.id.product_img_1);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpKouDaiBeiYongJinOpApi.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            ILFactory.getLoader().loadNet(pic1, HttpKouDaiBeiYongJinOpApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductModelKouDaiBeiYongJinOp model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), JumpKouDaiBeiYongJinOpH5Activity.class, bundle);
        }
    }
}
