package com.nfsthjsrtuae.fghserytuxfh.present;

import com.nfsthjsrtuae.fghserytuxfh.model.BaseRespModel;
import com.nfsthjsrtuae.fghserytuxfh.model.LoginRespModel;
import com.nfsthjsrtuae.fghserytuxfh.net.Api;
import com.nfsthjsrtuae.fghserytuxfh.ui.HomePageActivity;
import com.nfsthjsrtuae.fghserytuxfh.utils.SharedPreferencesUtilis;
import com.nfsthjsrtuae.fghserytuxfh.utils.StaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.net.NetError;
import com.nfsthjsrtuae.fghserytuxfh.net.XApi;
import com.nfsthjsrtuae.fghserytuxfh.net.ApiSubscriber;

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
