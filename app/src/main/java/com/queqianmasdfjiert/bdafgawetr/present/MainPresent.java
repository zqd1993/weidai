package com.queqianmasdfjiert.bdafgawetr.present;

import com.queqianmasdfjiert.bdafgawetr.net.Api;
import com.queqianmasdfjiert.bdafgawetr.ui.HomePageActivity;
import com.queqianmasdfjiert.bdafgawetr.model.BaseRespModel;
import com.queqianmasdfjiert.bdafgawetr.model.LoginRespModel;
import com.queqianmasdfjiert.bdafgawetr.utils.SharedPreferencesUtilis;
import com.queqianmasdfjiert.bdafgawetr.utils.StaticUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XPresent;
import com.queqianmasdfjiert.bdafgawetr.net.NetError;
import com.queqianmasdfjiert.bdafgawetr.net.XApi;
import com.queqianmasdfjiert.bdafgawetr.net.ApiSubscriber;

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
