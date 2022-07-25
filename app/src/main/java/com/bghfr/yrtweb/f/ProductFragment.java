package com.bghfr.yrtweb.f;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bghfr.yrtweb.a.WangYeActivity;
import com.bghfr.yrtweb.mvp.XActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.bghfr.yrtweb.R;
import com.bghfr.yrtweb.a.FragmentApader;
import com.bghfr.yrtweb.a.GoodsAdapter;
import com.bghfr.yrtweb.api.MyApi;
import com.bghfr.yrtweb.m.MainModel;
import com.bghfr.yrtweb.m.ShangPinModel;
import com.bghfr.yrtweb.mvp.XFragment;
import com.bghfr.yrtweb.net.ApiSubscriber;
import com.bghfr.yrtweb.net.NetError;
import com.bghfr.yrtweb.net.XApi;
import com.bghfr.yrtweb.u.BaseUtil;
import com.bghfr.yrtweb.u.PreferencesStaticOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductFragment extends XFragment {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.goods_fragment_top)
    View goodsFragmentTop;

    private int mobileType;

    private String phone;

    private ShangPinModel shangPinModel;

    private Bundle bundle;

    private GoodsAdapter mGoodsAdapter;

    private ArrayList<Fragment> mFragments;
    private String[] mTitlesArrays = {"热门推荐","小额贷款","高通过率","大额分期"};

    @Override
    public void initData(Bundle savedInstanceState) {
        getGoodsList();
        mFragments = new ArrayList<>();
        mFragments.add(new ListFragment());
        mFragments.add(new ListFragment());
        mFragments.add(new ListFragment());
        mFragments.add(new ListFragment());
        FragmentApader adapter = new FragmentApader(getChildFragmentManager(),mFragments, mTitlesArrays);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        goodsFragmentTop.setOnClickListener(v -> {
            goodsClick(shangPinModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void goodsClick(ShangPinModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencesStaticOpenUtil.getString("phone");
            MyApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toH5(model);
                        }

                        @Override
                        public void onNext(MainModel mainModel) {
                            toH5(model);
                        }
                    });
    }


    public void getGoodsList() {
            mobileType = PreferencesStaticOpenUtil.getInt("mobileType");
            MyApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel<List<ShangPinModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            BaseUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(MainModel<List<ShangPinModel>> mainModel) {
                            if (mainModel != null) {
                                if (mainModel.getCode() == 200 && mainModel.getData() != null) {
                                    if (mainModel.getData() != null && mainModel.getData().size() > 0) {
                                        shangPinModel = mainModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void toH5(ShangPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            BaseUtil.getValue((XActivity) getActivity(), WangYeActivity.class, bundle);
        }
    }

}
