package com.nsryryasdt.ioerdfjrtu.present;

import com.nsryryasdt.ioerdfjrtu.model.BaseRespModel;
import com.nsryryasdt.ioerdfjrtu.model.LoginRespModel;
import com.nsryryasdt.ioerdfjrtu.ui.HomePageActivity;
import com.nsryryasdt.ioerdfjrtu.utils.SharedPreferencesUtilis;
import com.nsryryasdt.ioerdfjrtu.utils.StaticUtil;
import com.nsryryasdt.ioerdfjrtu.mvp.XPresent;
import com.nsryryasdt.ioerdfjrtu.net.Api;
import com.nsryryasdt.ioerdfjrtu.net.NetError;
import com.nsryryasdt.ioerdfjrtu.net.XApi;
import com.nsryryasdt.ioerdfjrtu.net.ApiSubscriber;

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
