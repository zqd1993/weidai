package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaopresent;

import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.HomePageJiuJiJieTiaojghsdfActivity;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.BaseRespModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.LoginRespModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.JiuJiJieTiaojghsdfApi;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.SharedJiuJiJieTiaojghsdfPreferencesUtilis;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.StaticJiuJiJieTiaojghsdfUtil;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XPresent;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.NetError;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.XApi;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.ApiSubscriber;

public class MainJiuJiJieTiaojghsdfPresent extends XPresent<HomePageJiuJiJieTiaojghsdfActivity> {

    private String phone, ip;

    public void login() {
        phone = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("phone");
        ip = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("ip");
        JiuJiJieTiaojghsdfApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>getApiTransformer())
                .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>getScheduler())
                .compose(getV().<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticJiuJiJieTiaojghsdfUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
