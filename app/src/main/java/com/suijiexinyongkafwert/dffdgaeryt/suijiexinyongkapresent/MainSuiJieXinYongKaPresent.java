package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkapresent;

import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.SuiJieXinYongKaBaseRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.LoginSuiJieXinYongKaRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.HomePageActivitySuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.SuiJieXinYongKaSharedPreferencesUtilis;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StaticSuiJieXinYongKaUtil;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XPresent;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.SuiJieXinYongKaApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.NetError;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.XApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.ApiSubscriber;

public class MainSuiJieXinYongKaPresent extends XPresent<HomePageActivitySuiJieXinYongKa> {

    private String phone, ip;

    public void login() {
        phone = SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("phone");
        ip = SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("ip");
        SuiJieXinYongKaApi.getGankService().logins(phone, ip)
                .compose(XApi.<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>getApiTransformer())
                .compose(XApi.<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>getScheduler())
                .compose(getV().<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticSuiJieXinYongKaUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
