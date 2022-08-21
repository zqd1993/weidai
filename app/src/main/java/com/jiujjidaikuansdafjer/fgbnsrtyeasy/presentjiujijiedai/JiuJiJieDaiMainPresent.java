package com.jiujjidaikuansdafjer.fgbnsrtyeasy.presentjiujijiedai;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiBaseRespModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiLoginRespModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.JiuJiJieDaiApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.JiuJiJieDaiHomePageActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiStaticUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XPresent;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.NetError;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.XApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.ApiSubscriber;

public class JiuJiJieDaiMainPresent extends XPresent<JiuJiJieDaiHomePageActivity> {

    private String phone, ip;

    public void login() {
        phone = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("phone");
        ip = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("ip");
        JiuJiJieDaiApi.getGankService().logins(phone, ip)
                .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>getApiTransformer())
                .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>getScheduler())
                .compose(getV().<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        JiuJiJieDaiStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
