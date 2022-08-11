package com.aklsfasad.fsjhfkk.present;

import android.text.TextUtils;
import android.view.View;

import com.aklsfasad.fsjhfkk.model.BaseRespHuiMinModel;
import com.aklsfasad.fsjhfkk.model.GoodsHuiMinModel;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.mvp.XPresent;
import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.net.ApiSubscriber;
import com.aklsfasad.fsjhfkk.net.NetError;
import com.aklsfasad.fsjhfkk.net.XApi;
import com.aklsfasad.fsjhfkk.ui.fragment.HomePageFragmentHuiMin;

import java.util.List;


public class HomePagePresentHuiMin extends XPresent<HomePageFragmentHuiMin> {

    private int mobileType;

    private String phone;


    public void productClick(GoodsHuiMinModel model) {
        phone = SharedPreferencesUtilisHuiMin.getStringFromPref("phone");
        Api.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespHuiMinModel>getApiTransformer())
                .compose(XApi.<BaseRespHuiMinModel>getScheduler())
                .compose(getV().<BaseRespHuiMinModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespHuiMinModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebYouXinActivity(model);
                        StaticUtilHuiMin.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespHuiMinModel gankResults) {
                        getV().jumpWebYouXinActivity(model);
                    }
                });
    }


    public void productList() {
        mobileType = SharedPreferencesUtilisHuiMin.getIntFromPref("mobileType");
        Api.getGankService().productList(mobileType)
                .compose(XApi.<BaseRespHuiMinModel<List<GoodsHuiMinModel>>>getApiTransformer())
                .compose(XApi.<BaseRespHuiMinModel<List<GoodsHuiMinModel>>>getScheduler())
                .compose(getV().<BaseRespHuiMinModel<List<GoodsHuiMinModel>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespHuiMinModel<List<GoodsHuiMinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        getV().noDataFl.setVisibility(View.VISIBLE);
                        StaticUtilHuiMin.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespHuiMinModel<List<GoodsHuiMinModel>> gankResults) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                    getV().setModel(gankResults.getData().get(0));
                                    getV().initGoodsItemAdapter(gankResults.getData());
                                } else {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            } else {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                        } else {
                            getV().noDataFl.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
