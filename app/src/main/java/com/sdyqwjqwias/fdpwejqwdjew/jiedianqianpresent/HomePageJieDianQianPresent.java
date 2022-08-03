package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianpresent;

import android.view.View;

import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.BaseRespModelJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.JieDianQianGoodsModel;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianfragment.HomePageJieDianQianFragment;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StaticJieDianQianUtil;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XPresent;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.JieDianQianApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.ApiSubscriber;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.XApi;

import java.util.List;


public class HomePageJieDianQianPresent extends XPresent<HomePageJieDianQianFragment> {

    private int mobileType;

    private String phone;

    public void productList() {
//            getV().goodsModels.clear();
            mobileType = JieDianQianSharedPreferencesUtilis.getIntFromPref("mobileType");
            phone = JieDianQianSharedPreferencesUtilis.getStringFromPref("phone");
            JieDianQianApi.getGankService().productList(mobileType, phone)
                    .compose(XApi.<BaseRespModelJieDianQian<List<JieDianQianGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieDianQian<List<JieDianQianGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelJieDianQian<List<JieDianQianGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieDianQian<List<JieDianQianGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterJieDianQian == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticJieDianQianUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieDianQian<List<JieDianQianGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData());
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        if (getV().goodsItemAdapterJieDianQian == null) {
                                            getV().noDataFl.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (getV().goodsItemAdapterJieDianQian == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().goodsItemAdapterJieDianQian == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
    }

    public void productClick(JieDianQianGoodsModel model) {
            getV().jieDianQianGoodsModels.clear();
            phone = JieDianQianSharedPreferencesUtilis.getStringFromPref("phone");
            JieDianQianApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelJieDianQian>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieDianQian>getScheduler())
                    .compose(getV().<BaseRespModelJieDianQian>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieDianQian>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticJieDianQianUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieDianQian gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
    }

}
