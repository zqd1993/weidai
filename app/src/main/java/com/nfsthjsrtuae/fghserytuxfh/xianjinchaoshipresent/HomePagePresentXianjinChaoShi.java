package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshipresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.BaseRespModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.GoodsModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.RequXianjinChaoShiModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.XianjinChaoShiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.fragment.HomePageFragmentXianjinChaoShi;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresentXianjinChaoShi extends XPresent<HomePageFragmentXianjinChaoShi> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("token");
            RequXianjinChaoShiModel model = new RequXianjinChaoShiModel();
            model.setToken(token);
            RequestBody body = XianjinChaoShiStaticUtil.createBody(new Gson().toJson(model));
            ApiXianjinChaoShi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>getScheduler())
                    .compose(getV().<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            XianjinChaoShiStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>> gankResults) {
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

    public void productClick(GoodsModelXianjinChaoShi model) {
        phone = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("phone");
        ApiXianjinChaoShi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelXianjinChaoShi>getApiTransformer())
                .compose(XApi.<BaseRespModelXianjinChaoShi>getScheduler())
                .compose(getV().<BaseRespModelXianjinChaoShi>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        XianjinChaoShiStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelXianjinChaoShi gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("token");
            RequXianjinChaoShiModel model = new RequXianjinChaoShiModel();
            model.setToken(token);
            RequestBody body = XianjinChaoShiStaticUtil.createBody(new Gson().toJson(model));
            ApiXianjinChaoShi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>getScheduler())
                    .compose(getV().<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        KuaiDianFenQiDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianjinChaoShi<List<GoodsModelXianjinChaoShi>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsModelXianjinChaoShi = gankResults.getTop();
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
