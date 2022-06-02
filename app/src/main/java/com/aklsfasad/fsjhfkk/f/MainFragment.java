package com.aklsfasad.fsjhfkk.f;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.a.DlActivity;
import com.aklsfasad.fsjhfkk.a.JumpH5Activity;
import com.aklsfasad.fsjhfkk.a.StartPageActivity;
import com.aklsfasad.fsjhfkk.api.HttpApi;
import com.aklsfasad.fsjhfkk.imageloader.ILFactory;
import com.aklsfasad.fsjhfkk.imageloader.ILoader;
import com.aklsfasad.fsjhfkk.m.BaseModel;
import com.aklsfasad.fsjhfkk.m.ProductModel;
import com.aklsfasad.fsjhfkk.mvp.XFragment;
import com.aklsfasad.fsjhfkk.net.ApiSubscriber;
import com.aklsfasad.fsjhfkk.net.NetError;
import com.aklsfasad.fsjhfkk.net.XApi;
import com.aklsfasad.fsjhfkk.u.MyToast;
import com.aklsfasad.fsjhfkk.u.OpenUtil;
import com.aklsfasad.fsjhfkk.u.PreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;

public class MainFragment extends XFragment {

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

    private ProductModel productModel;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            toWeb(productModel);
        });
        jx_bg.setOnClickListener(v -> {
            toWeb(productModel);
        });
        goodsListLl.setOnClickListener(v -> {
            toWeb(productModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModel model) {
        phone = PreferencesOpenUtil.getString("phone");
        HttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = PreferencesOpenUtil.getInt("mobileType");
        HttpApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<List<ProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenUtil.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseModel<List<ProductModel>> baseModel) {
                        setRefreshing.setRefreshing(false);
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productModel = baseModel.getData().get(0);
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

    private void addProductView(List<ProductModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductModel model : mList) {
            View view = View.inflate(getActivity(), R.layout.layout_product_view, null);
            TextView timeTv = view.findViewById(R.id.time_tv);
            TextView peopleNumberTv = view.findViewById(R.id.people_number_tv);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View click_view = view.findViewById(R.id.click_view);
            timeTv.setText(model.getDes() + "个月");
            peopleNumberTv.setText(String.valueOf(model.getPassingRate()));
            ILFactory.getLoader().loadNet(pic, HttpApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            click_view.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    public void toWeb(ProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtil.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }
}
