package com.srysfghsrty.mkdtyusaert.present;

import com.srysfghsrty.mkdtyusaert.model.BaseRespModel;
import com.srysfghsrty.mkdtyusaert.model.LoginRespModel;
import com.srysfghsrty.mkdtyusaert.ui.HomePageActivity;
import com.srysfghsrty.mkdtyusaert.utils.SharedPreferencesUtilis;
import com.srysfghsrty.mkdtyusaert.utils.StaticUtil;
import com.srysfghsrty.mkdtyusaert.mvp.XPresent;
import com.srysfghsrty.mkdtyusaert.net.Api;
import com.srysfghsrty.mkdtyusaert.net.NetError;
import com.srysfghsrty.mkdtyusaert.net.XApi;
import com.srysfghsrty.mkdtyusaert.net.ApiSubscriber;

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
