package com.srysfghsrty.mkdtyusaert.wanrongxinyongkapresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.BaseRespModelWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.WanRongXinYongKaGoodsModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.RequWanRongXinYongKaModel;
import com.srysfghsrty.mkdtyusaert.mvp.XPresent;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiSubscriber;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.NetError;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.XApi;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.fragment.ProductFragment;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.SharedPreferencesUtilisWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.StaticWanRongXinYongKaUtil;

import java.util.List;

import okhttp3.RequestBody;


public class ProductPresentWanRongXinYongKa extends XPresent<ProductFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("token");
            RequWanRongXinYongKaModel model = new RequWanRongXinYongKaModel();
            model.setToken(token);
            RequestBody body = StaticWanRongXinYongKaUtil.createBody(new Gson().toJson(model));
            ApiWanRongXinYongKa.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<List<WanRongXinYongKaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<List<WanRongXinYongKaGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelWanRongXinYongKa<List<WanRongXinYongKaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWanRongXinYongKa<List<WanRongXinYongKaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticWanRongXinYongKaUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWanRongXinYongKa<List<WanRongXinYongKaGoodsModel>> gankResults) {
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

    public void productClick(WanRongXinYongKaGoodsModel model) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            phone = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("phone");
            ApiWanRongXinYongKa.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelWanRongXinYongKa>getApiTransformer())
                    .compose(XApi.<BaseRespModelWanRongXinYongKa>getScheduler())
                    .compose(getV().<BaseRespModelWanRongXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWanRongXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticWanRongXinYongKaUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWanRongXinYongKa gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

}
