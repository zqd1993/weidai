package com.ruyijiekuandfwetdg.nnrdtydfgsd.presentyijiekuandfwetr;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.BaseRespModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfGoodsModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfRequModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStaticUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XPresent;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.RuYiJieKuanAdgFsdfApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.ApiSubscriber;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.NetError;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.XApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.fragmentyijiekuandfwetr.RuYiJieKuanAdgFsdfHomePageFragment;

import java.util.List;

import okhttp3.RequestBody;


public class RuYiJieKuanAdgFsdfHomePagePresent extends XPresent<RuYiJieKuanAdgFsdfHomePageFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("token");
            RuYiJieKuanAdgFsdfRequModel model = new RuYiJieKuanAdgFsdfRequModel();
            model.setToken(token);
            RequestBody body = RuYiJieKuanAdgFsdfStaticUtil.createBody(new Gson().toJson(model));
            RuYiJieKuanAdgFsdfApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            RuYiJieKuanAdgFsdfStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>> gankResults) {
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

    public void productClick(RuYiJieKuanAdgFsdfGoodsModel model) {
        phone = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("phone");
        RuYiJieKuanAdgFsdfApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf>getApiTransformer())
                .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf>getScheduler())
                .compose(getV().<BaseRespModelRuYiJieKuanAdgFsdf>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        RuYiJieKuanAdgFsdfStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelRuYiJieKuanAdgFsdf gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("token");
            RuYiJieKuanAdgFsdfRequModel model = new RuYiJieKuanAdgFsdfRequModel();
            model.setToken(token);
            RequestBody body = RuYiJieKuanAdgFsdfStaticUtil.createBody(new Gson().toJson(model));
            RuYiJieKuanAdgFsdfApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topRuYiJieKuanAdgFsdfGoodsModel = gankResults.getTop();
                                            getV().money_num_tv.setText(gankResults.getTop().getMax_money());
                                            getV().info_tv.setText(gankResults.getTop().getInfo());
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

}
