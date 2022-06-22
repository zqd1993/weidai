package com.tryrbdfbv.grtregdfh.present;

import com.tryrbdfbv.grtregdfh.model.BaseRespModel;
import com.tryrbdfbv.grtregdfh.model.LoginRespModel;
import com.tryrbdfbv.grtregdfh.ui.HomePageActivity;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilis;
import com.tryrbdfbv.grtregdfh.utils.StaticUtil;
import com.tryrbdfbv.grtregdfh.mvp.XPresent;
import com.tryrbdfbv.grtregdfh.net.Api;
import com.tryrbdfbv.grtregdfh.net.NetError;
import com.tryrbdfbv.grtregdfh.net.XApi;
import com.tryrbdfbv.grtregdfh.net.ApiSubscriber;

public class MainPresent extends XPresent<HomePageActivity> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        ip = SharedPreferencesUtilis.getStringFromPref("ip");
        Api.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModel<LoginRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModel<LoginRespModel>>getScheduler())
                .compose(getV().<BaseRespModel<LoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<LoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModel<LoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
