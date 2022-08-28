package com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqpresent;

import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqnet.YjjdFqjqApi;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqui.HomePageActivityYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqmodel.YjjdFqjqBaseRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqmodel.YjjdFqjqLoginRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqutils.StaticYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfgh.mvp.XPresent;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqnet.NetError;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqnet.XApi;
import com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqnet.ApiSubscriber;

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
