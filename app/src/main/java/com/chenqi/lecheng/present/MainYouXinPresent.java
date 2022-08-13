package com.chenqi.lecheng.present;

import android.text.TextUtils;

import com.chenqi.lecheng.model.BaseRespYouXinModel;
import com.chenqi.lecheng.model.LoginRespYouXinModel;
import com.chenqi.lecheng.ui.HomePageYouXinActivity;
import com.chenqi.lecheng.utils.SharedPreferencesYouXinUtilis;
import com.chenqi.lecheng.utils.StaticYouXinUtil;
import com.chenqi.lecheng.mvp.XPresent;
import com.chenqi.lecheng.net.Api;
import com.chenqi.lecheng.net.NetError;
import com.chenqi.lecheng.net.XApi;
import com.chenqi.lecheng.net.ApiSubscriber;

public class MainYouXinPresent extends XPresent<HomePageYouXinActivity> {

    private String phone, ip;

    public void login() {
            phone = SharedPreferencesYouXinUtilis.getStringFromPref("phone");
            ip = SharedPreferencesYouXinUtilis.getStringFromPref("ip");
            Api.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespYouXinModel<LoginRespYouXinModel>>getApiTransformer())
                    .compose(XApi.<BaseRespYouXinModel<LoginRespYouXinModel>>getScheduler())
                    .compose(getV().<BaseRespYouXinModel<LoginRespYouXinModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespYouXinModel<LoginRespYouXinModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticYouXinUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespYouXinModel<LoginRespYouXinModel> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
    }

}
