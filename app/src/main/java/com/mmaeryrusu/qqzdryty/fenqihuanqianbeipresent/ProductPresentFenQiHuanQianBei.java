package com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.FenQiHuanQianBeiGoodsModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.RequFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeifragment.FenQiHuanQianBeiProductFragment;
import com.mmaeryrusu.qqzdryty.mvp.XPresent;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.FenQiHuanQianBeiApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.SharedPreferencesFenQiHuanQianBeiUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiStaticUtil;

import java.util.List;

import okhttp3.RequestBody;


public class ProductPresentFenQiHuanQianBei extends XPresent<FenQiHuanQianBeiProductFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("token");
            RequFenQiHuanQianBeiModel model = new RequFenQiHuanQianBeiModel();
            model.setToken(token);
            RequestBody body = FenQiHuanQianBeiStaticUtil.createBody(new Gson().toJson(model));
            FenQiHuanQianBeiApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            FenQiHuanQianBeiStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>> gankResults) {
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

    public void productClick(FenQiHuanQianBeiGoodsModel model) {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("phone");
            FenQiHuanQianBeiApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            FenQiHuanQianBeiStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("token");
            RequFenQiHuanQianBeiModel model = new RequFenQiHuanQianBeiModel();
            model.setToken(token);
            RequestBody body = FenQiHuanQianBeiStaticUtil.createBody(new Gson().toJson(model));
            FenQiHuanQianBeiApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel<List<FenQiHuanQianBeiGoodsModel>> gankResults) {
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
