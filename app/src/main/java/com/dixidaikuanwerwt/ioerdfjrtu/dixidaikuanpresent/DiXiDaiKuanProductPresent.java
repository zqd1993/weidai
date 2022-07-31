package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanpresent;

import android.text.TextUtils;

import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.GoodsModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.fragment.ProductDiXiDaiKuanFragment;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.StaticDiXiDaiKuanUtil;
import com.google.gson.Gson;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.BaseRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.RequDiXiDaiKuanModel;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XPresent;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.DiXiDaiKuanApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.ApiSubscriber;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.NetError;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.XApi;

import java.util.List;

import okhttp3.RequestBody;


public class DiXiDaiKuanProductPresent extends XPresent<ProductDiXiDaiKuanFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("token");
            RequDiXiDaiKuanModel model = new RequDiXiDaiKuanModel();
            model.setToken(token);
            RequestBody body = StaticDiXiDaiKuanUtil.createBody(new Gson().toJson(model));
            DiXiDaiKuanApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>getScheduler())
                    .compose(getV().<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticDiXiDaiKuanUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>> gankResults) {
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

    public void productClick(GoodsModelDiXiDaiKuan model) {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("phone");
            DiXiDaiKuanApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelDiXiDaiKuan>getApiTransformer())
                    .compose(XApi.<BaseRespModelDiXiDaiKuan>getScheduler())
                    .compose(getV().<BaseRespModelDiXiDaiKuan>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticDiXiDaiKuanUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDiXiDaiKuan gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("token");
            RequDiXiDaiKuanModel model = new RequDiXiDaiKuanModel();
            model.setToken(token);
            RequestBody body = StaticDiXiDaiKuanUtil.createBody(new Gson().toJson(model));
            DiXiDaiKuanApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>getScheduler())
                    .compose(getV().<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticDiXiDaiKuanUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDiXiDaiKuan<List<GoodsModelDiXiDaiKuan>> gankResults) {
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
