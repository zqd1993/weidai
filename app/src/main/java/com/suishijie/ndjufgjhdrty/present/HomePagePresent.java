package com.suishijie.ndjufgjhdrty.present;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.suishijie.ndjufgjhdrty.model.BaseRespModel;
import com.suishijie.ndjufgjhdrty.model.GoodsModel;
import com.suishijie.ndjufgjhdrty.model.RequModel;
import com.suishijie.ndjufgjhdrty.utils.SharedPreferencesUtilis;
import com.suishijie.ndjufgjhdrty.utils.StaticUtil;
import com.suishijie.ndjufgjhdrty.mvp.XPresent;
import com.suishijie.ndjufgjhdrty.net.Api;
import com.suishijie.ndjufgjhdrty.net.ApiSubscriber;
import com.suishijie.ndjufgjhdrty.net.NetError;
import com.suishijie.ndjufgjhdrty.net.XApi;
import com.suishijie.ndjufgjhdrty.ui.fragment.HomePageFragment;

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
                                            if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
                                                Glide.with(getV()).load(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

}
