package com.chenqi.lecheng.present;

import android.text.TextUtils;
import android.view.View;

import com.chenqi.lecheng.model.BaseRespYouXinModel;
import com.chenqi.lecheng.model.GoodsWinAModel;
import com.chenqi.lecheng.utils.SharedPreferencesYouXinUtilis;
import com.chenqi.lecheng.utils.StaticYouXinUtil;
import com.chenqi.lecheng.mvp.XPresent;
import com.chenqi.lecheng.net.Api;
import com.chenqi.lecheng.net.ApiSubscriber;
import com.chenqi.lecheng.net.NetError;
import com.chenqi.lecheng.net.XApi;
import com.chenqi.lecheng.ui.fragment.HomePageYouXinFragment;

import java.util.List;


public class HomePageYouXinPresent extends XPresent<HomePageYouXinFragment> {

    private int mobileType;

    private String phone;

    public void productList() {
        if (!TextUtils.isEmpty(Api.API_BASE_URL)) {
            mobileType = SharedPreferencesYouXinUtilis.getIntFromPref("mobileType");
            Api.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespYouXinModel<List<GoodsWinAModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespYouXinModel<List<GoodsWinAModel>>>getScheduler())
                    .compose(getV().<BaseRespYouXinModel<List<GoodsWinAModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespYouXinModel<List<GoodsWinAModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().miaoJieGoodsItemAdapter == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticYouXinUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespYouXinModel<List<GoodsWinAModel>> gankResults) {
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
    }

    public void productClick(GoodsWinAModel model) {
        if (!TextUtils.isEmpty(Api.API_BASE_URL)) {
            phone = SharedPreferencesYouXinUtilis.getStringFromPref("phone");
            Api.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespYouXinModel>getApiTransformer())
                    .compose(XApi.<BaseRespYouXinModel>getScheduler())
                    .compose(getV().<BaseRespYouXinModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespYouXinModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebYouXinActivity(model);
                            StaticYouXinUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespYouXinModel gankResults) {
                            getV().jumpWebYouXinActivity(model);
                        }
                    });
        }
    }

}
