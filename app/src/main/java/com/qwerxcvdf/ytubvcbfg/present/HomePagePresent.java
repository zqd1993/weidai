package com.qwerxcvdf.ytubvcbfg.present;

import android.view.View;

import com.qwerxcvdf.ytubvcbfg.model.BaseRespModel;
import com.qwerxcvdf.ytubvcbfg.model.GoodsModel;
import com.qwerxcvdf.ytubvcbfg.utils.SharedPreferencesUtilis;
import com.qwerxcvdf.ytubvcbfg.utils.StaticUtil;
import com.qwerxcvdf.ytubvcbfg.mvp.XPresent;
import com.qwerxcvdf.ytubvcbfg.net.Api;
import com.qwerxcvdf.ytubvcbfg.net.ApiSubscriber;
import com.qwerxcvdf.ytubvcbfg.net.NetError;
import com.qwerxcvdf.ytubvcbfg.net.XApi;
import com.qwerxcvdf.ytubvcbfg.ui.fragment.HomePageFragment;

import java.util.List;


public class HomePagePresent extends XPresent<HomePageFragment> {

    private int mobileType;

    private String phone;

    public void productList() {
        mobileType = SharedPreferencesUtilis.getIntFromPref("mobileType");
        Api.getGankService().productList(mobileType)
                .compose(XApi.<BaseRespModel<List<GoodsModel>>>getApiTransformer())
                .compose(XApi.<BaseRespModel<List<GoodsModel>>>getScheduler())
                .compose(getV().<BaseRespModel<List<GoodsModel>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<List<GoodsModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (getV().miaoJieGoodsItemAdapter == null) {
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
                                    if (getV().miaoJieGoodsItemAdapter == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().miaoJieGoodsItemAdapter == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (getV().miaoJieGoodsItemAdapter == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    public void productClick(GoodsModel model) {
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
