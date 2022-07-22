package com.xg.qdk.f;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xg.qdk.a.WangYeActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.xg.qdk.R;
import com.xg.qdk.a.FragmentApader;
import com.xg.qdk.a.GoodsAdapter;
import com.xg.qdk.api.MyApi;
import com.xg.qdk.m.MainModel;
import com.xg.qdk.m.ShangPinModel;
import com.xg.qdk.mvp.XActivity;
import com.xg.qdk.mvp.XFragment;
import com.xg.qdk.net.ApiSubscriber;
import com.xg.qdk.net.NetError;
import com.xg.qdk.net.XApi;
import com.xg.qdk.u.BaseUtil;
import com.xg.qdk.u.PreferencesStaticOpenUtil;

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
    private String[] mTitlesArrays = {"热门推荐", "小额贷款", "高通过率", "大额分期"};

    @Override
    public void initData(Bundle savedInstanceState) {
        getGoodsList();
        mFragments = new ArrayList<>();
        mFragments.add(new ListFragment());
        mFragments.add(new ListFragment());
        mFragments.add(new ListFragment());
        mFragments.add(new ListFragment());
        FragmentApader adapter = new FragmentApader(getChildFragmentManager(), mFragments, mTitlesArrays);
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
        if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
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
    }


    public void getGoodsList() {
        if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesStaticOpenUtil.getInt("mobileType");
            phone = PreferencesStaticOpenUtil.getString("phone");
            MyApi.getInterfaceUtils().productList(mobileType, phone)
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
