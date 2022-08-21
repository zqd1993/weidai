package com.jiekuanzhijiasdoert.fghirtidfks.presentjiekuanzhijia;

import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.BaseRespModelJieKuanZhiJia;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.JieKuanZhiJiaLoginRespModel;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.JieKuanZhiJiaApi;
import com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.HomePageActivityJieKuanZhiJia;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaStaticUtil;
import com.jiekuanzhijiasdoert.fghirtidfks.mvp.XPresent;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.NetError;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.XApi;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.ApiSubscriber;

public class MainPresentJieKuanZhiJia extends XPresent<HomePageActivityJieKuanZhiJia> {

    private String phone, ip;

    public void login() {
        phone = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("phone");
        ip = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("ip");
        JieKuanZhiJiaApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>getScheduler())
                .compose(getV().<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        JieKuanZhiJiaStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
