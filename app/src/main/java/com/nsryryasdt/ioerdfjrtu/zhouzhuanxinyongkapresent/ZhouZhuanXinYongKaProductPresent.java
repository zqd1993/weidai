package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.BaseRespModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.GoodsZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.RequZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.mvp.XPresent;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ApiSubscriber;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.NetError;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.XApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkafragment.ProductFragment;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StaticZhouZhuanXinYongKaUtil;

import java.util.List;

import okhttp3.RequestBody;


public class ZhouZhuanXinYongKaProductPresent extends XPresent<ProductFragment> {

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
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
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
    }

}
