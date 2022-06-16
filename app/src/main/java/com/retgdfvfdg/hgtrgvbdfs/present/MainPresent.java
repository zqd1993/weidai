package com.retgdfvfdg.hgtrgvbdfs.present;

import com.retgdfvfdg.hgtrgvbdfs.model.BaseRespModel;
import com.retgdfvfdg.hgtrgvbdfs.model.LoginRespModel;
import com.retgdfvfdg.hgtrgvbdfs.ui.HomePageActivity;
import com.retgdfvfdg.hgtrgvbdfs.utils.SharedPreferencesUtilis;
import com.retgdfvfdg.hgtrgvbdfs.utils.StaticUtil;
import com.retgdfvfdg.hgtrgvbdfs.mvp.XPresent;
import com.retgdfvfdg.hgtrgvbdfs.net.Api;
import com.retgdfvfdg.hgtrgvbdfs.net.NetError;
import com.retgdfvfdg.hgtrgvbdfs.net.XApi;
import com.retgdfvfdg.hgtrgvbdfs.net.ApiSubscriber;

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
