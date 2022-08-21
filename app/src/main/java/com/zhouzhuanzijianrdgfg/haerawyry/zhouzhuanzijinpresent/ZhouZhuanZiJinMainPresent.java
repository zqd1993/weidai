package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinpresent;

import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginRespModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ZhouZhuanZiJinApi;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.ZhouZhuanZiJinHomePageActivity;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinStaticUtil;
import com.zhouzhuanzijianrdgfg.haerawyry.mvp.XPresent;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.NetError;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.XApi;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ApiSubscriber;

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
