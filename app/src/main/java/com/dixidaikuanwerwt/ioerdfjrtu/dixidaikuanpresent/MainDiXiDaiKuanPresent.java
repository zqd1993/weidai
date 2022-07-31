package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanpresent;

import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.BaseRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.LoginRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.DiXiDaiKuanApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.HomePageDiXiDaiKuanActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.StaticDiXiDaiKuanUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XPresent;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.NetError;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.XApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.ApiSubscriber;

public class MainDiXiDaiKuanPresent extends XPresent<HomePageDiXiDaiKuanActivity> {

    private String phone, ip;

    public void login() {
        phone = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("phone");
        ip = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("ip");
        DiXiDaiKuanApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>getApiTransformer())
                .compose(XApi.<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>getScheduler())
                .compose(getV().<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticDiXiDaiKuanUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
