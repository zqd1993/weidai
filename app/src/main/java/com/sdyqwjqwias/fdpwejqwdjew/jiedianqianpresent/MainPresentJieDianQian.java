package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianpresent;

import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.BaseRespModelJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.LoginRespModelJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.HomePageJieDianQianActivity;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StaticJieDianQianUtil;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XPresent;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.JieDianQianApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.ApiSubscriber;

public class MainPresentJieDianQian extends XPresent<HomePageJieDianQianActivity> {

    private String phone, ip;

    public void login() {
            phone = JieDianQianSharedPreferencesUtilis.getStringFromPref("phone");
            ip = JieDianQianSharedPreferencesUtilis.getStringFromPref("ip");
            JieDianQianApi.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>getScheduler())
                    .compose(getV().<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticJieDianQianUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieDianQian<LoginRespModelJieDianQian> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
    }

}
