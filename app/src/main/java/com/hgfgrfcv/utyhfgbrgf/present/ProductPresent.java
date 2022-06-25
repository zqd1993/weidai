package com.hgfgrfcv.utyhfgbrgf.present;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hgfgrfcv.utyhfgbrgf.model.BaseRespModel;
import com.hgfgrfcv.utyhfgbrgf.model.GoodsModel;
import com.hgfgrfcv.utyhfgbrgf.model.RequModel;
import com.hgfgrfcv.utyhfgbrgf.mvp.XPresent;
import com.hgfgrfcv.utyhfgbrgf.net.Api;
import com.hgfgrfcv.utyhfgbrgf.net.ApiSubscriber;
import com.hgfgrfcv.utyhfgbrgf.net.NetError;
import com.hgfgrfcv.utyhfgbrgf.net.XApi;
import com.hgfgrfcv.utyhfgbrgf.ui.fragment.ProductFragment;
import com.hgfgrfcv.utyhfgbrgf.utils.SharedPreferencesUtilis;
import com.hgfgrfcv.utyhfgbrgf.utils.StaticUtil;

import java.util.List;

import okhttp3.RequestBody;


public class ProductPresent extends XPresent<ProductFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilis.getStringFromPref("token");
            RequModel model = new RequModel();
            model.setToken(token);
            RequestBody body = StaticUtil.createBody(new Gson().toJson(model));
            Api.getGankService().productList(body)
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<List<GoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModel<List<GoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<List<GoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<List<GoodsModel>> gankResults) {
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

    public void productClick(GoodsModel model) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = SharedPreferencesUtilis.getStringFromPref("phone");
            Api.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModel>getApiTransformer())
                    .compose(XApi.<BaseRespModel>getScheduler())
                    .compose(getV().<BaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

}
