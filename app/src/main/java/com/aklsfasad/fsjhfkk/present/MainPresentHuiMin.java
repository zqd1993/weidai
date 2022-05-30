package com.aklsfasad.fsjhfkk.present;

import com.aklsfasad.fsjhfkk.model.BaseRespHuiMinModel;
import com.aklsfasad.fsjhfkk.model.LoginRespHuiMinModel;
import com.aklsfasad.fsjhfkk.ui.HomePageActivityHuiMin;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.mvp.XPresent;
import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.net.NetError;
import com.aklsfasad.fsjhfkk.net.XApi;
import com.aklsfasad.fsjhfkk.net.ApiSubscriber;

public class MainPresentHuiMin extends XPresent<HomePageActivityHuiMin> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesUtilisHuiMin.getStringFromPref("phone");
        ip = SharedPreferencesUtilisHuiMin.getStringFromPref("ip");
        Api.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespHuiMinModel<LoginRespHuiMinModel>>getApiTransformer())
                .compose(XApi.<BaseRespHuiMinModel<LoginRespHuiMinModel>>getScheduler())
                .compose(getV().<BaseRespHuiMinModel<LoginRespHuiMinModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespHuiMinModel<LoginRespHuiMinModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilHuiMin.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespHuiMinModel<LoginRespHuiMinModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
