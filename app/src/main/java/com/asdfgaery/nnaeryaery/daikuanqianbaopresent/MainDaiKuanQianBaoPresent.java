package com.asdfgaery.nnaeryaery.daikuanqianbaopresent;

import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.BaseRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.LoginRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.DaiKuanQianBaoApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.HomePageDaiKuanQianBaoActivity;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.StaticDaiKuanQianBaoUtil;
import com.asdfgaery.nnaeryaery.mvp.XPresent;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.NetError;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.XApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.ApiSubscriber;

public class MainDaiKuanQianBaoPresent extends XPresent<HomePageDaiKuanQianBaoActivity> {

    private String phone, ip;

    public void login() {
        phone = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("phone");
        ip = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("ip");
        DaiKuanQianBaoApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>getApiTransformer())
                .compose(XApi.<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>getScheduler())
                .compose(getV().<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticDaiKuanQianBaoUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
