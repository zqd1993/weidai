package com.jiujjidaikuansdafjer.fgbnsrtyeasy.presentjiujijiedai;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiBaseRespModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiGoodsModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiRequModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiStaticUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XPresent;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.JiuJiJieDaiApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.ApiSubscriber;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.NetError;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.XApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.fragmentjiujijiedai.JiuJiJieDaiHomePageFragment;

import java.util.List;

import okhttp3.RequestBody;


public class JiuJiJieDaiHomePagePresent extends XPresent<JiuJiJieDaiHomePageFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("token");
            JiuJiJieDaiRequModel model = new JiuJiJieDaiRequModel();
            model.setToken(token);
            RequestBody body = JiuJiJieDaiStaticUtil.createBody(new Gson().toJson(model));
            JiuJiJieDaiApi.getGankService().productList(body)
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>getScheduler())
                    .compose(getV().<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            JiuJiJieDaiStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>> gankResults) {
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

    public void productClick(JiuJiJieDaiGoodsModel model) {
        phone = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("phone");
        JiuJiJieDaiApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<JiuJiJieDaiBaseRespModel>getApiTransformer())
                .compose(XApi.<JiuJiJieDaiBaseRespModel>getScheduler())
                .compose(getV().<JiuJiJieDaiBaseRespModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        JiuJiJieDaiStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(JiuJiJieDaiBaseRespModel gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("token");
            JiuJiJieDaiRequModel model = new JiuJiJieDaiRequModel();
            model.setToken(token);
            RequestBody body = JiuJiJieDaiStaticUtil.createBody(new Gson().toJson(model));
            JiuJiJieDaiApi.getGankService().aindex(body)
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>getScheduler())
                    .compose(getV().<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(JiuJiJieDaiBaseRespModel<List<JiuJiJieDaiGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        getV().topJiuJiJieDaiGoodsModel = gankResults.getTop();
                                        getV().money_num_tv.setText(gankResults.getTop().getMax_money());
                                        getV().info_tv.setText(gankResults.getTop().getInfo());
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
//                                                Glide.with(getV()).load(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
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
