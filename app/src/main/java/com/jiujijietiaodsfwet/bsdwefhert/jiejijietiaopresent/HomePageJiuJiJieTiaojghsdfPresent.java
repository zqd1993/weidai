package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaopresent;

import android.text.TextUtils;

import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoui.jiejijietiaofragment.HomePageJiuJiJieTiaojghsdfFragment;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.GoodsModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.JiuJiJieTiaojghsdfApi;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.SharedJiuJiJieTiaojghsdfPreferencesUtilis;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.StaticJiuJiJieTiaojghsdfUtil;
import com.google.gson.Gson;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.BaseRespModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.RequJiuJiJieTiaojghsdfModel;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XPresent;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.ApiSubscriber;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.NetError;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.XApi;

import java.util.List;

import okhttp3.RequestBody;


public class HomePageJiuJiJieTiaojghsdfPresent extends XPresent<HomePageJiuJiJieTiaojghsdfFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("token");
            RequJiuJiJieTiaojghsdfModel model = new RequJiuJiJieTiaojghsdfModel();
            model.setToken(token);
            RequestBody body = StaticJiuJiJieTiaojghsdfUtil.createBody(new Gson().toJson(model));
            JiuJiJieTiaojghsdfApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>getScheduler())
                    .compose(getV().<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticJiuJiJieTiaojghsdfUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>> gankResults) {
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

    public void productClick(GoodsModelJiuJiJieTiaojghsdf model) {
        phone = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("phone");
        JiuJiJieTiaojghsdfApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf>getApiTransformer())
                .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf>getScheduler())
                .compose(getV().<BaseRespModelJiuJiJieTiaojghsdf>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticJiuJiJieTiaojghsdfUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelJiuJiJieTiaojghsdf gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("token");
            RequJiuJiJieTiaojghsdfModel model = new RequJiuJiJieTiaojghsdfModel();
            model.setToken(token);
            RequestBody body = StaticJiuJiJieTiaojghsdfUtil.createBody(new Gson().toJson(model));
            JiuJiJieTiaojghsdfApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>getScheduler())
                    .compose(getV().<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticJiuJiJieTiaojghsdfUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJiuJiJieTiaojghsdf<List<GoodsModelJiuJiJieTiaojghsdf>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsModelJiuJiJieTiaojghsdf = gankResults.getTop();
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
