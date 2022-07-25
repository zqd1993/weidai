package com.werwerd.ertegdfg.present;

import android.text.TextUtils;
import android.view.View;

import com.werwerd.ertegdfg.model.BaseRespYouXinModel;
import com.werwerd.ertegdfg.model.GoodsWinAModel;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StaticYouXinUtil;
import com.werwerd.ertegdfg.mvp.XPresent;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.net.ApiSubscriber;
import com.werwerd.ertegdfg.net.NetError;
import com.werwerd.ertegdfg.net.XApi;
import com.werwerd.ertegdfg.ui.fragment.HomePageYouXinFragment;

import java.util.List;


public class HomePageYouXinPresent extends XPresent<HomePageYouXinFragment> {

    private int mobileType;

    private String phone;

    public void productList() {
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

    public void productClick(GoodsWinAModel model) {
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
