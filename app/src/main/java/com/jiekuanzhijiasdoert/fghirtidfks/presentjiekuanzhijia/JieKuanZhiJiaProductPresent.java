package com.jiekuanzhijiasdoert.fghirtidfks.presentjiekuanzhijia;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.BaseRespModelJieKuanZhiJia;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.JieKuanZhiJiaGoodsModel;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.JieKuanZhiJiaRequModel;
import com.jiekuanzhijiasdoert.fghirtidfks.mvp.XPresent;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.JieKuanZhiJiaApi;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.ApiSubscriber;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.NetError;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.XApi;
import com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.fragmentjiekuanzhijia.JieKuanZhiJiaProductFragment;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaStaticUtil;

import java.util.List;

import okhttp3.RequestBody;


public class JieKuanZhiJiaProductPresent extends XPresent<JieKuanZhiJiaProductFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("token");
            JieKuanZhiJiaRequModel model = new JieKuanZhiJiaRequModel();
            model.setToken(token);
            RequestBody body = JieKuanZhiJiaStaticUtil.createBody(new Gson().toJson(model));
            JieKuanZhiJiaApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            JieKuanZhiJiaStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>> gankResults) {
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

    public void productClick(JieKuanZhiJiaGoodsModel model) {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("phone");
            JieKuanZhiJiaApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelJieKuanZhiJia>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia>getScheduler())
                    .compose(getV().<BaseRespModelJieKuanZhiJia>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            JieKuanZhiJiaStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("token");
            JieKuanZhiJiaRequModel model = new JieKuanZhiJiaRequModel();
            model.setToken(token);
            RequestBody body = JieKuanZhiJiaStaticUtil.createBody(new Gson().toJson(model));
            JieKuanZhiJiaApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>> gankResults) {
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
