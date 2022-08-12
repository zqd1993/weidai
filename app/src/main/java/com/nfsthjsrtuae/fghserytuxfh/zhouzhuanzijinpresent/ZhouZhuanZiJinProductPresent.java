package com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinpresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinGoodsModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinRequModel;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.ZhouZhuanZiJinApi;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinfragment.ZhouZhuanZiJinProductFragment;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinStaticUtil;

import java.util.List;

import okhttp3.RequestBody;


public class ZhouZhuanZiJinProductPresent extends XPresent<ZhouZhuanZiJinProductFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("token");
            ZhouZhuanZiJinRequModel model = new ZhouZhuanZiJinRequModel();
            model.setToken(token);
            RequestBody body = ZhouZhuanZiJinStaticUtil.createBody(new Gson().toJson(model));
            ZhouZhuanZiJinApi.getGankService().productList(body)
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>getScheduler())
                    .compose(getV().<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            ZhouZhuanZiJinStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>> gankResults) {
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

    public void productClick(ZhouZhuanZiJinGoodsModel model) {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("phone");
            ZhouZhuanZiJinApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel>getScheduler())
                    .compose(getV().<ZhouZhuanZiJinBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            ZhouZhuanZiJinStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinBaseRespModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("token");
            ZhouZhuanZiJinRequModel model = new ZhouZhuanZiJinRequModel();
            model.setToken(token);
            RequestBody body = ZhouZhuanZiJinStaticUtil.createBody(new Gson().toJson(model));
            ZhouZhuanZiJinApi.getGankService().aindex(body)
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>getScheduler())
                    .compose(getV().<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinBaseRespModel<List<ZhouZhuanZiJinGoodsModel>> gankResults) {
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
