package com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.GoodsFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.RequFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.mvp.XPresent;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fragment.FenQiHuanQianBeiProductFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StaticUtilFenQiHuanQianBei;

import java.util.List;

import okhttp3.RequestBody;


public class ProductPresentFenQiHuanQianBei extends XPresent<FenQiHuanQianBeiProductFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("token");
            RequFenQiHuanQianBeiModel model = new RequFenQiHuanQianBeiModel();
            model.setToken(token);
            RequestBody body = StaticUtilFenQiHuanQianBei.createBody(new Gson().toJson(model));
            ApiFenQiHuanQianBei.getGankService().productList(body)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticUtilFenQiHuanQianBei.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>> gankResults) {
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

    public void productClick(GoodsFenQiHuanQianBeiModel model) {
        if (!TextUtils.isEmpty(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("phone");
            ApiFenQiHuanQianBei.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtilFenQiHuanQianBei.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("token");
            RequFenQiHuanQianBeiModel model = new RequFenQiHuanQianBeiModel();
            model.setToken(token);
            RequestBody body = StaticUtilFenQiHuanQianBei.createBody(new Gson().toJson(model));
            ApiFenQiHuanQianBei.getGankService().aindex(body)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtilFenQiHuanQianBei.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel<List<GoodsFenQiHuanQianBeiModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
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

}
