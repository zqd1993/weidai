package com.ufaofqsbxo.uunllhykas.f;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ufaofqsbxo.uunllhykas.a.WangYeActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.ufaofqsbxo.uunllhykas.R;
import com.ufaofqsbxo.uunllhykas.a.FragmentApader;
import com.ufaofqsbxo.uunllhykas.a.GoodsAdapter;
import com.ufaofqsbxo.uunllhykas.api.MyApi;
import com.ufaofqsbxo.uunllhykas.m.MainModel;
import com.ufaofqsbxo.uunllhykas.m.ShangPinModel;
import com.ufaofqsbxo.uunllhykas.mvp.XFragment;
import com.ufaofqsbxo.uunllhykas.net.ApiSubscriber;
import com.ufaofqsbxo.uunllhykas.net.NetError;
import com.ufaofqsbxo.uunllhykas.net.XApi;
import com.ufaofqsbxo.uunllhykas.u.BaseUtil;
import com.ufaofqsbxo.uunllhykas.u.PreferencesStaticOpenUtil;

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
    }

    public void toH5(ShangPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            BaseUtil.jumpPage(getActivity(), WangYeActivity.class, bundle);
        }
    }

}
