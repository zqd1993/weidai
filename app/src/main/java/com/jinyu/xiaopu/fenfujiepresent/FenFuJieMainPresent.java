package com.jinyu.xiaopu.fenfujiepresent;

import com.jinyu.xiaopu.fenfujiemodel.BaseRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.LoginRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujieui.HomePageFenFuJieActivity;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;
import com.jinyu.xiaopu.mvp.XPresent;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;
import com.jinyu.xiaopu.fenfujienet.NetError;
import com.jinyu.xiaopu.fenfujienet.XApi;
import com.jinyu.xiaopu.fenfujienet.ApiSubscriber;

public class FenFuJieMainPresent extends XPresent<HomePageFenFuJieActivity> {

    private String phone, ip;

    public void login() {
            phone = SharedPreferencesUtilisFenFuJie.getStringFromPref("phone");
            ip = SharedPreferencesUtilisFenFuJie.getStringFromPref("ip");
            FenFuJieApi.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>getApiTransformer())
                    .compose(XApi.<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>getScheduler())
                    .compose(getV().<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticFenFuJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelFenFuJie<LoginRespModelFenFuJie> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
    }

}
