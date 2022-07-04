package com.dfgderv.erterqweq.present;

import android.text.TextUtils;

import com.dfgderv.erterqweq.model.BaseRespModel;
import com.dfgderv.erterqweq.model.LoginRespModel;
import com.dfgderv.erterqweq.ui.HomePageActivity;
import com.dfgderv.erterqweq.utils.SharedPreferencesUtilis;
import com.dfgderv.erterqweq.utils.StaticUtil;
import com.dfgderv.erterqweq.mvp.XPresent;
import com.dfgderv.erterqweq.net.Api;
import com.dfgderv.erterqweq.net.NetError;
import com.dfgderv.erterqweq.net.XApi;
import com.dfgderv.erterqweq.net.ApiSubscriber;

public class MainPresent extends XPresent<HomePageActivity> {

    private String phone, ip;

    public void login() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            phone = SharedPreferencesUtilis.getStringFromPref("phone");
            ip = SharedPreferencesUtilis.getStringFromPref("ip");
            Api.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespModel<LoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<LoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModel<LoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<LoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<LoginRespModel> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
        }
    }

}
