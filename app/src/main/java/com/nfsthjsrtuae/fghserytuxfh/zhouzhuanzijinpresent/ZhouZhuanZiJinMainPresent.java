package com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinpresent;

import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginRespModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.ZhouZhuanZiJinApi;
import com.nfsthjsrtuae.fghserytuxfh.ui.ZhouZhuanZiJinHomePageActivity;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.ApiSubscriber;

public class ZhouZhuanZiJinMainPresent extends XPresent<ZhouZhuanZiJinHomePageActivity> {

    private String phone, ip;

    public void login() {
        phone = ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("phone");
        ip = ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("ip");
        ZhouZhuanZiJinApi.getGankService().logins(phone, ip)
                .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>getApiTransformer())
                .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>getScheduler())
                .compose(getV().<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        ZhouZhuanZiJinStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
