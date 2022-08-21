package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent;

import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.BaseRespModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.LoginRespZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.HomePageZhouZhuanXinYongKaActivity;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StaticZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.mvp.XPresent;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.NetError;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.XApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ApiSubscriber;

public class MainZhouZhuanXinYongKaPresent extends XPresent<HomePageZhouZhuanXinYongKaActivity> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("phone");
        ip = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("ip");
        ZhouZhuanXinYongKaApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>getScheduler())
                .compose(getV().<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticZhouZhuanXinYongKaUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
