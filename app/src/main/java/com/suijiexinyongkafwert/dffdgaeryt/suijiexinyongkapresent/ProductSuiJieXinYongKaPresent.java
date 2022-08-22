package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkapresent;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.SuiJieXinYongKaBaseRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.GoodsSuiJieXinYongKaModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.RequSuiJieXinYongKaModel;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XPresent;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.SuiJieXinYongKaApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.ApiSubscriber;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.NetError;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.XApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkafragment.ProductSuiJieXinYongKaFragment;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.SuiJieXinYongKaSharedPreferencesUtilis;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StaticSuiJieXinYongKaUtil;

import java.util.List;

import okhttp3.RequestBody;


public class ProductSuiJieXinYongKaPresent extends XPresent<ProductSuiJieXinYongKaFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("token");
            RequSuiJieXinYongKaModel model = new RequSuiJieXinYongKaModel();
            model.setToken(token);
            RequestBody body = StaticSuiJieXinYongKaUtil.createBody(new Gson().toJson(model));
            SuiJieXinYongKaApi.getGankService().productList(body)
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<List<GoodsSuiJieXinYongKaModel>>>getApiTransformer())
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<List<GoodsSuiJieXinYongKaModel>>>getScheduler())
                    .compose(getV().<SuiJieXinYongKaBaseRespModel<List<GoodsSuiJieXinYongKaModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<SuiJieXinYongKaBaseRespModel<List<GoodsSuiJieXinYongKaModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticSuiJieXinYongKaUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(SuiJieXinYongKaBaseRespModel<List<GoodsSuiJieXinYongKaModel>> gankResults) {
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

    public void productClick(GoodsSuiJieXinYongKaModel model) {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("phone");
            SuiJieXinYongKaApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel>getApiTransformer())
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel>getScheduler())
                    .compose(getV().<SuiJieXinYongKaBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<SuiJieXinYongKaBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticSuiJieXinYongKaUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(SuiJieXinYongKaBaseRespModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

}
