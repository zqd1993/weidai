package com.queqianmasdfjiert.bdafgawetr.presentqueqianmaboss;

import android.text.TextUtils;

import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.GoodsQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.fragmentqueqianmaboss.QueQianMaBossHomePageFragment;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.SharedPreferencesUtilisQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StaticQueQianMaBossUtil;
import com.google.gson.Gson;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.BaseRespQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.RequQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.mvp.XPresent;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiSubscriber;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.NetError;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.XApi;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresentQueQianMaBoss extends XPresent<QueQianMaBossHomePageFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("token");
            RequQueQianMaBossModel model = new RequQueQianMaBossModel();
            model.setToken(token);
            RequestBody body = StaticQueQianMaBossUtil.createBody(new Gson().toJson(model));
            ApiQueQianMaBoss.getGankService().productList(body)
                    .compose(XApi.<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>getScheduler())
                    .compose(getV().<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticQueQianMaBossUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>> gankResults) {
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

    public void productClick(GoodsQueQianMaBossModel model) {
        phone = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("phone");
        ApiQueQianMaBoss.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespQueQianMaBossModel>getApiTransformer())
                .compose(XApi.<BaseRespQueQianMaBossModel>getScheduler())
                .compose(getV().<BaseRespQueQianMaBossModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticQueQianMaBossUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespQueQianMaBossModel gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("token");
            RequQueQianMaBossModel model = new RequQueQianMaBossModel();
            model.setToken(token);
            RequestBody body = StaticQueQianMaBossUtil.createBody(new Gson().toJson(model));
            ApiQueQianMaBoss.getGankService().aindex(body)
                    .compose(XApi.<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>getScheduler())
                    .compose(getV().<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticQueQianMaBossUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQueQianMaBossModel<List<GoodsQueQianMaBossModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsQueQianMaBossModel = gankResults.getTop();
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
