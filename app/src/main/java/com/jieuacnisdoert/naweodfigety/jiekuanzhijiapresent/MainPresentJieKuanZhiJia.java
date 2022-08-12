package com.jieuacnisdoert.naweodfigety.jiekuanzhijiapresent;

import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.BaseRespModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaLoginRespModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.JieKuanZhiJiaApi;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.HomePageActivityJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaStaticUtil;
import com.jieuacnisdoert.naweodfigety.mvp.XPresent;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.NetError;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.XApi;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.ApiSubscriber;

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
