package com.srysfghsrty.mkdtyusaert.present;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.srysfghsrty.mkdtyusaert.model.BaseRespModel;
import com.srysfghsrty.mkdtyusaert.model.GoodsModel;
import com.srysfghsrty.mkdtyusaert.model.RequModel;
import com.srysfghsrty.mkdtyusaert.utils.SharedPreferencesUtilis;
import com.srysfghsrty.mkdtyusaert.utils.StaticUtil;
import com.srysfghsrty.mkdtyusaert.mvp.XPresent;
import com.srysfghsrty.mkdtyusaert.net.Api;
import com.srysfghsrty.mkdtyusaert.net.ApiSubscriber;
import com.srysfghsrty.mkdtyusaert.net.NetError;
import com.srysfghsrty.mkdtyusaert.net.XApi;
import com.srysfghsrty.mkdtyusaert.ui.fragment.HomePageFragment;

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
                                            getV().max_money_tv.setText(String.valueOf(gankResults.getTop().getMax_money()));
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
