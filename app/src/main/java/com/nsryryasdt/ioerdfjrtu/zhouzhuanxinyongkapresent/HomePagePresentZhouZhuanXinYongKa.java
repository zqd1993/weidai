package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.BaseRespModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.GoodsZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.RequZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StaticZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.mvp.XPresent;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ApiSubscriber;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.NetError;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.XApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkafragment.HomePageFragment;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresentZhouZhuanXinYongKa extends XPresent<HomePageFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("token");
            RequZhouZhuanXinYongKaModel model = new RequZhouZhuanXinYongKaModel();
            model.setToken(token);
            RequestBody body = StaticZhouZhuanXinYongKaUtil.createBody(new Gson().toJson(model));
            ZhouZhuanXinYongKaApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>getScheduler())
                    .compose(getV().<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticZhouZhuanXinYongKaUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>> gankResults) {
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

    public void productClick(GoodsZhouZhuanXinYongKaModel model) {
        phone = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("phone");
        ZhouZhuanXinYongKaApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelZhouZhuanXinYongKa>getApiTransformer())
                .compose(XApi.<BaseRespModelZhouZhuanXinYongKa>getScheduler())
                .compose(getV().<BaseRespModelZhouZhuanXinYongKa>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticZhouZhuanXinYongKaUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelZhouZhuanXinYongKa gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("token");
            RequZhouZhuanXinYongKaModel model = new RequZhouZhuanXinYongKaModel();
            model.setToken(token);
            RequestBody body = StaticZhouZhuanXinYongKaUtil.createBody(new Gson().toJson(model));
            ZhouZhuanXinYongKaApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>getScheduler())
                    .compose(getV().<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticZhouZhuanXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelZhouZhuanXinYongKa<List<GoodsZhouZhuanXinYongKaModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsZhouZhuanXinYongKaModel = gankResults.getTop();
                                            if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
                                                Glide.with(getV()).load(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
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
