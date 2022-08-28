package com.queqianmasdfjiert.bdafgawetr.present;

import android.text.TextUtils;

import com.queqianmasdfjiert.bdafgawetr.model.GoodsModel;
import com.queqianmasdfjiert.bdafgawetr.net.Api;
import com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaofragment.HomePageFragment;
import com.queqianmasdfjiert.bdafgawetr.utils.SharedPreferencesUtilis;
import com.queqianmasdfjiert.bdafgawetr.utils.StaticUtil;
import com.google.gson.Gson;
import com.queqianmasdfjiert.bdafgawetr.model.BaseRespModel;
import com.queqianmasdfjiert.bdafgawetr.model.RequModel;
import com.queqianmasdfjiert.bdafgawetr.mvp.XPresent;
import com.queqianmasdfjiert.bdafgawetr.net.ApiSubscriber;
import com.queqianmasdfjiert.bdafgawetr.net.NetError;
import com.queqianmasdfjiert.bdafgawetr.net.XApi;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresent extends XPresent<HomePageFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilis.getStringFromPref("token");
            RequModel model = new RequModel();
            model.setToken(token);
            RequestBody body = StaticUtil.createBody(new Gson().toJson(model));
            Api.getGankService().productList(body)
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModel<List<GoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<List<GoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<List<GoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                }
                            }
                        }
                    });
        }
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

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilis.getStringFromPref("token");
            RequModel model = new RequModel();
            model.setToken(token);
            RequestBody body = StaticUtil.createBody(new Gson().toJson(model));
            Api.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModel<List<GoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<List<GoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<List<GoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsModel = gankResults.getTop();
                                            getV().money_num_tv.setText(gankResults.getTop().getMax_money());
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

}
