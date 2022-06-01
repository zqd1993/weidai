package com.qwerxcvdf.ytubvcbfg.present;

import com.qwerxcvdf.ytubvcbfg.model.BaseRespModel;
import com.qwerxcvdf.ytubvcbfg.model.LoginRespModel;
import com.qwerxcvdf.ytubvcbfg.ui.HomePageActivity;
import com.qwerxcvdf.ytubvcbfg.utils.SharedPreferencesUtilis;
import com.qwerxcvdf.ytubvcbfg.utils.StaticUtil;
import com.qwerxcvdf.ytubvcbfg.mvp.XPresent;
import com.qwerxcvdf.ytubvcbfg.net.Api;
import com.qwerxcvdf.ytubvcbfg.net.NetError;
import com.qwerxcvdf.ytubvcbfg.net.XApi;
import com.qwerxcvdf.ytubvcbfg.net.ApiSubscriber;

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
