package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaipresent;

import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.BaseRespModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.LoginKuaiDianFenQiDaiRespModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.HomePageActivityKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.KuaiDianFenQiDaiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiSubscriber;

public class KuaiDianFenQiDaiMainPresent extends XPresent<HomePageActivityKuaiDianFenQiDai> {

    private String phone, ip;

    public void login() {
        phone = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("phone");
        ip = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("ip");
        ApiKuaiDianFenQiDai.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>getScheduler())
                .compose(getV().<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        KuaiDianFenQiDaiStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

}
