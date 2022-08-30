package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqpresent;

import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.YjjdFqjqApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.HomePageActivityYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqBaseRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqLoginRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.StaticYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XPresent;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.NetError;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.XApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.ApiSubscriber;

public class MainYjjdFqjqPresent extends XPresent<HomePageActivityYjjdFqjq> {

    private String phone, ip;

    public void login() {
        phone = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("phone");
        ip = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("ip");
        YjjdFqjqApi.getGankService().logins(phone, ip)
                .compose(XApi.<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>getApiTransformer())
                .compose(XApi.<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>getScheduler())
                .compose(getV().<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticYjjdFqjqUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
