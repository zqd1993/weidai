package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqpresent;

import android.text.TextUtils;

import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.GoodsModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.YjjdFqjqApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqfragment.YjjdFqjqHomePageFragment;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.StaticYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;
import com.google.gson.Gson;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqBaseRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.RequYjjdFqjqModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XPresent;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.ApiSubscriber;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.NetError;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.XApi;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresentYjjdFqjq extends XPresent<YjjdFqjqHomePageFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("token");
            RequYjjdFqjqModel model = new RequYjjdFqjqModel();
            model.setToken(token);
            RequestBody body = StaticYjjdFqjqUtil.createBody(new Gson().toJson(model));
            YjjdFqjqApi.getGankService().productList(body)
                    .compose(XApi.<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>getApiTransformer())
                    .compose(XApi.<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>getScheduler())
                    .compose(getV().<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticYjjdFqjqUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>> gankResults) {
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

    public void productClick(GoodsModelYjjdFqjq model) {
        phone = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("phone");
        YjjdFqjqApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<YjjdFqjqBaseRespModel>getApiTransformer())
                .compose(XApi.<YjjdFqjqBaseRespModel>getScheduler())
                .compose(getV().<YjjdFqjqBaseRespModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticYjjdFqjqUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(YjjdFqjqBaseRespModel gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("token");
            RequYjjdFqjqModel model = new RequYjjdFqjqModel();
            model.setToken(token);
            RequestBody body = StaticYjjdFqjqUtil.createBody(new Gson().toJson(model));
            YjjdFqjqApi.getGankService().aindex(body)
                    .compose(XApi.<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>getApiTransformer())
                    .compose(XApi.<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>getScheduler())
                    .compose(getV().<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticYjjdFqjqUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsModelYjjdFqjq = gankResults.getTop();
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
