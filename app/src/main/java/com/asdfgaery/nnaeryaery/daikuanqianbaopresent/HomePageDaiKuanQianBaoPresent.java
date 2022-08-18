package com.asdfgaery.nnaeryaery.daikuanqianbaopresent;

import android.text.TextUtils;

import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.GoodsModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.DaiKuanQianBaoApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.StaticDaiKuanQianBaoUtil;
import com.google.gson.Gson;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.BaseRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.RequDaiKuanQianBaoModel;
import com.asdfgaery.nnaeryaery.mvp.XPresent;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.ApiSubscriber;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.NetError;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.XApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaofragment.HomePageDaiKuanQianBaoFragment;

import java.util.List;

import okhttp3.RequestBody;


public class HomePageDaiKuanQianBaoPresent extends XPresent<HomePageDaiKuanQianBaoFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("token");
            RequDaiKuanQianBaoModel model = new RequDaiKuanQianBaoModel();
            model.setToken(token);
            RequestBody body = StaticDaiKuanQianBaoUtil.createBody(new Gson().toJson(model));
            DaiKuanQianBaoApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>getScheduler())
                    .compose(getV().<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticDaiKuanQianBaoUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>> gankResults) {
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

    public void productClick(GoodsModelDaiKuanQianBao model) {
        phone = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("phone");
        DaiKuanQianBaoApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelDaiKuanQianBao>getApiTransformer())
                .compose(XApi.<BaseRespModelDaiKuanQianBao>getScheduler())
                .compose(getV().<BaseRespModelDaiKuanQianBao>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticDaiKuanQianBaoUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDaiKuanQianBao gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("token");
            RequDaiKuanQianBaoModel model = new RequDaiKuanQianBaoModel();
            model.setToken(token);
            RequestBody body = StaticDaiKuanQianBaoUtil.createBody(new Gson().toJson(model));
            DaiKuanQianBaoApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>getScheduler())
                    .compose(getV().<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticDiXiDaiKuanUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaiKuanQianBao<List<GoodsModelDaiKuanQianBao>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsModelDaiKuanQianBao = gankResults.getTop();
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
