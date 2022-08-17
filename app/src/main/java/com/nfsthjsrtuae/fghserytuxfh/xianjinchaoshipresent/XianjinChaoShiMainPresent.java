package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshipresent;

import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.BaseRespModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.LoginXianjinChaoShiRespModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.HomePageActivityXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.XianjinChaoShiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiSubscriber;

public class XianjinChaoShiMainPresent extends XPresent<HomePageActivityXianjinChaoShi> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("phone");
        ip = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("ip");
        ApiXianjinChaoShi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>getScheduler())
                .compose(getV().<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        XianjinChaoShiStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
