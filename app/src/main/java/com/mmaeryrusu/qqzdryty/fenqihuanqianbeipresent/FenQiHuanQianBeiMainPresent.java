package com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.LoginRespModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.FenQiHuanQianBeiApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.HomePageActivityFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.SharedPreferencesFenQiHuanQianBeiUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiStaticUtil;
import com.mmaeryrusu.qqzdryty.mvp.XPresent;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;

public class FenQiHuanQianBeiMainPresent extends XPresent<HomePageActivityFenQiHuanQianBei> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("phone");
        ip = SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("ip");
        FenQiHuanQianBeiApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>getApiTransformer())
                .compose(XApi.<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>getScheduler())
                .compose(getV().<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>() {
                    @Override
                    protected void onFail(NetError error) {
                        FenQiHuanQianBeiStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
