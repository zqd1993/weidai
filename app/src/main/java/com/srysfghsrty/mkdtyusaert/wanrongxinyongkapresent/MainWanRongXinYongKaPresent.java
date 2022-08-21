package com.srysfghsrty.mkdtyusaert.wanrongxinyongkapresent;

import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.BaseRespModelWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.LoginRespModelWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.HomePageActivityWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.SharedPreferencesUtilisWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.StaticWanRongXinYongKaUtil;
import com.srysfghsrty.mkdtyusaert.mvp.XPresent;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.NetError;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.XApi;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiSubscriber;

public class MainWanRongXinYongKaPresent extends XPresent<HomePageActivityWanRongXinYongKa> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("phone");
        ip = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("ip");
        ApiWanRongXinYongKa.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>getApiTransformer())
                .compose(XApi.<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>getScheduler())
                .compose(getV().<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticWanRongXinYongKaUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
