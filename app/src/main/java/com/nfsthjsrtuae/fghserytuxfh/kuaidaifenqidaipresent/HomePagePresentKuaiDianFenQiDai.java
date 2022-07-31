package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaipresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.BaseRespModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.GoodsModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.RequKuaiDianFenQiDaiModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.KuaiDianFenQiDaiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.fragment.HomePageFragmentKuaiDianFenQiDai;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresentKuaiDianFenQiDai extends XPresent<HomePageFragmentKuaiDianFenQiDai> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("token");
            RequKuaiDianFenQiDaiModel model = new RequKuaiDianFenQiDaiModel();
            model.setToken(token);
            RequestBody body = KuaiDianFenQiDaiStaticUtil.createBody(new Gson().toJson(model));
            ApiKuaiDianFenQiDai.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>getScheduler())
                    .compose(getV().<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            KuaiDianFenQiDaiStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>> gankResults) {
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

    public void productClick(GoodsModelKuaiDianFenQiDai model) {
        phone = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("phone");
        ApiKuaiDianFenQiDai.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelKuaiDianFenQiDai>getApiTransformer())
                .compose(XApi.<BaseRespModelKuaiDianFenQiDai>getScheduler())
                .compose(getV().<BaseRespModelKuaiDianFenQiDai>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        KuaiDianFenQiDaiStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelKuaiDianFenQiDai gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("token");
            RequKuaiDianFenQiDaiModel model = new RequKuaiDianFenQiDaiModel();
            model.setToken(token);
            RequestBody body = KuaiDianFenQiDaiStaticUtil.createBody(new Gson().toJson(model));
            ApiKuaiDianFenQiDai.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>getScheduler())
                    .compose(getV().<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        KuaiDianFenQiDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKuaiDianFenQiDai<List<GoodsModelKuaiDianFenQiDai>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsModelKuaiDianFenQiDai = gankResults.getTop();
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
