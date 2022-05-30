package com.rihdkauecgh.plihgnytrvfws.present;

import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.ui.BasePagerFragment;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.net.NetError;
import com.rihdkauecgh.plihgnytrvfws.net.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.net.XApi;

/**
 * Created by wanglei on 2016/12/31.
 */

public class PBasePager extends XPresent<BasePagerFragment> {
    protected static final int PAGE_SIZE = 10;


    public void loadData() {
        Api.getGankService().getGankData()
                .compose(XApi.<BaseRespModel<ConfigModel>>getApiTransformer())
                .compose(XApi.<BaseRespModel<ConfigModel>>getScheduler())
                .compose(getV().<BaseRespModel<ConfigModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<ConfigModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                    @Override
                    public void onNext(BaseRespModel<ConfigModel> gankResults) {
//                        getV().showData(page, gankResults);
                    }
                });
    }
}
