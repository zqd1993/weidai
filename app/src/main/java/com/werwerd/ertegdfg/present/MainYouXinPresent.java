package com.werwerd.ertegdfg.present;

import com.werwerd.ertegdfg.model.BaseRespYouXinModel;
import com.werwerd.ertegdfg.model.LoginRespYouXinModel;
import com.werwerd.ertegdfg.ui.HomePageYouXinActivity;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StaticYouXinUtil;
import com.werwerd.ertegdfg.mvp.XPresent;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.net.NetError;
import com.werwerd.ertegdfg.net.XApi;
import com.werwerd.ertegdfg.net.ApiSubscriber;

public class MainYouXinPresent extends XPresent<HomePageYouXinActivity> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesYouXinUtilis.getStringFromPref("phone");
        ip = SharedPreferencesYouXinUtilis.getStringFromPref("ip");
        Api.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespYouXinModel<LoginRespYouXinModel>>getApiTransformer())
                .compose(XApi.<BaseRespYouXinModel<LoginRespYouXinModel>>getScheduler())
                .compose(getV().<BaseRespYouXinModel<LoginRespYouXinModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespYouXinModel<LoginRespYouXinModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticYouXinUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespYouXinModel<LoginRespYouXinModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
