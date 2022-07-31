package com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.LoginRespModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.HomePageFenQiHuanQianBeiActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StaticUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.mvp.XPresent;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;

public class MainFenQiHuanQianBeiPresent extends XPresent<HomePageFenQiHuanQianBeiActivity> {

    private String phone, ip;

    public void login() {
        phone = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("phone");
        ip = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("ip");
        ApiFenQiHuanQianBei.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>getApiTransformer())
                .compose(XApi.<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>getScheduler())
                .compose(getV().<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilFenQiHuanQianBei.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
