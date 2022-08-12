package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaipresent;

import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiBaseRespModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiLoginRespModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.JiuJiJieDaiApi;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.JiuJiJieDaiHomePageActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiStaticUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XPresent;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.NetError;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.XApi;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.ApiSubscriber;

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
