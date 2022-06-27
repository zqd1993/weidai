package com.rihdkauecgh.plihgnytrvfws.present;

import android.text.TextUtils;
import android.view.View;

import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.GoodsModel;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtil;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.net.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.net.NetError;
import com.rihdkauecgh.plihgnytrvfws.net.XApi;
import com.rihdkauecgh.plihgnytrvfws.ui.fragment.HomePageFragment;

import java.util.List;


public class HomePagePresent extends XPresent<HomePageFragment> {

    private int mobileType;

    private String phone;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            mobileType = SharedPreferencesUtilis.getIntFromPref("mobileType");
            Api.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModel<List<GoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<List<GoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapter == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<List<GoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        if (getV().goodsItemAdapter == null) {
                                            getV().noDataFl.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (getV().goodsItemAdapter == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().goodsItemAdapter == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
        }
    }

    public void productClick(GoodsModel model) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            phone = SharedPreferencesUtilis.getStringFromPref("phone");
            Api.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModel>getApiTransformer())
                    .compose(XApi.<BaseRespModel>getScheduler())
                    .compose(getV().<BaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

}
