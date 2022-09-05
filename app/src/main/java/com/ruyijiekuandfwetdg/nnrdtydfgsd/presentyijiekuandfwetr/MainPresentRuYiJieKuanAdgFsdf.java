package com.ruyijiekuandfwetdg.nnrdtydfgsd.presentyijiekuandfwetr;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.BaseRespModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginRespModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.RuYiJieKuanAdgFsdfApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.HomePageActivityRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStaticUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XPresent;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.NetError;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.XApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.ApiSubscriber;

public class MainPresentRuYiJieKuanAdgFsdf extends XPresent<HomePageActivityRuYiJieKuanAdgFsdf> {

    private String phone, ip;

    public void login() {
        phone = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("phone");
        ip = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("ip");
        RuYiJieKuanAdgFsdfApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>getScheduler())
                .compose(getV().<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        RuYiJieKuanAdgFsdfStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
