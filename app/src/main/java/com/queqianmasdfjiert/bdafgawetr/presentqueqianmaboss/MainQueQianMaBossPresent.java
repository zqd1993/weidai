package com.queqianmasdfjiert.bdafgawetr.presentqueqianmaboss;

import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.HomePageActivityQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.BaseRespQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.LoginQueQianMaBossRespModel;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.SharedPreferencesUtilisQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StaticQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XPresent;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.NetError;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.XApi;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiSubscriber;

public class MainQueQianMaBossPresent extends XPresent<HomePageActivityQueQianMaBoss> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("phone");
        ip = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("ip");
        ApiQueQianMaBoss.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>getScheduler())
                .compose(getV().<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticQueQianMaBossUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
