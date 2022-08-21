package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinpresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinGoodsModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinRequModel;
import com.zhouzhuanzijianrdgfg.haerawyry.mvp.XPresent;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ZhouZhuanZiJinApi;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ApiSubscriber;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.NetError;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.XApi;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.zhouzhuanzijinfragment.ZhouZhuanZiJinProductFragment;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinStaticUtil;

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
