package com.jinyu.xiaopu.fenfujiepresent;

import android.view.View;

import com.jinyu.xiaopu.fenfujiemodel.BaseRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.GoodsFenFuJieModel;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;
import com.jinyu.xiaopu.mvp.XPresent;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;
import com.jinyu.xiaopu.fenfujienet.ApiSubscriber;
import com.jinyu.xiaopu.fenfujienet.NetError;
import com.jinyu.xiaopu.fenfujienet.XApi;
import com.jinyu.xiaopu.fenfujieui.fenfujiefragment.HomePageFragmentFenFuJie;

import java.util.List;


public class HomePagePresentFenFuJie extends XPresent<HomePageFragmentFenFuJie> {

    private int mobileType;

    private String phone;

    public void productList() {
        mobileType = SharedPreferencesUtilisFenFuJie.getIntFromPref("mobileType");
        phone = SharedPreferencesUtilisFenFuJie.getStringFromPref("phone");
        getV().goodsFenFuJieModel = null;
        FenFuJieApi.getGankService().productList(mobileType, phone)
                .compose(XApi.<BaseRespModelFenFuJie<List<GoodsFenFuJieModel>>>getApiTransformer())
                .compose(XApi.<BaseRespModelFenFuJie<List<GoodsFenFuJieModel>>>getScheduler())
                .compose(getV().<BaseRespModelFenFuJie<List<GoodsFenFuJieModel>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<List<GoodsFenFuJieModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (getV().goodsItemAdapterFenFuJie == null) {
                            getV().noDataFl.setVisibility(View.VISIBLE);
                        }
                        StaticFenFuJieUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelFenFuJie<List<GoodsFenFuJieModel>> gankResults) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                    getV().rvy.setVisibility(View.VISIBLE);
                                    getV().noDataFl.setVisibility(View.GONE);
                                    getV().setModel(gankResults.getData().get(0));
                                    getV().initGoodsItemAdapter(gankResults.getData());
                                } else {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                    getV().rvy.setVisibility(View.GONE);
                                }
                            } else {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                                getV().rvy.setVisibility(View.GONE);
                            }
                        } else {
                            getV().noDataFl.setVisibility(View.VISIBLE);
                            getV().rvy.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void productClick(GoodsFenFuJieModel model) {
        phone = SharedPreferencesUtilisFenFuJie.getStringFromPref("phone");
        FenFuJieApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelFenFuJie>getApiTransformer())
                .compose(XApi.<BaseRespModelFenFuJie>getScheduler())
                .compose(getV().<BaseRespModelFenFuJie>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelFenFuJie>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticFenFuJieUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelFenFuJie gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

}
