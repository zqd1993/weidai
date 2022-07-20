package com.suishijie.ndjufgjhdrty.present;

import com.suishijie.ndjufgjhdrty.model.BaseRespModel;
import com.suishijie.ndjufgjhdrty.model.LoginRespModel;
import com.suishijie.ndjufgjhdrty.ui.HomePageActivity;
import com.suishijie.ndjufgjhdrty.utils.SharedPreferencesUtilis;
import com.suishijie.ndjufgjhdrty.utils.StaticUtil;
import com.suishijie.ndjufgjhdrty.mvp.XPresent;
import com.suishijie.ndjufgjhdrty.net.Api;
import com.suishijie.ndjufgjhdrty.net.NetError;
import com.suishijie.ndjufgjhdrty.net.XApi;
import com.suishijie.ndjufgjhdrty.net.ApiSubscriber;

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
