package com.hgfgrfcv.utyhfgbrgf.present;

import com.hgfgrfcv.utyhfgbrgf.model.BaseRespModel;
import com.hgfgrfcv.utyhfgbrgf.model.LoginRespModel;
import com.hgfgrfcv.utyhfgbrgf.ui.HomePageActivity;
import com.hgfgrfcv.utyhfgbrgf.utils.SharedPreferencesUtilis;
import com.hgfgrfcv.utyhfgbrgf.utils.StaticUtil;
import com.hgfgrfcv.utyhfgbrgf.mvp.XPresent;
import com.hgfgrfcv.utyhfgbrgf.net.Api;
import com.hgfgrfcv.utyhfgbrgf.net.NetError;
import com.hgfgrfcv.utyhfgbrgf.net.XApi;
import com.hgfgrfcv.utyhfgbrgf.net.ApiSubscriber;

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
