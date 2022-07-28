package com.fjsdkqwj.pfdioewjnsd.test.rxbus;

import android.os.Bundle;

import com.blankj.rxbus.RxBus;
import com.fjsdkqwj.pfdioewjnsd.event.BusProvider;
import com.fjsdkqwj.pfdioewjnsd.mvp.XActivity;

/**
 * Created by wanglei on 2017/1/30.
 */

public class RxBusActivity extends XActivity {

    @Override
    public void initData(Bundle savedInstanceState) {

        BusProvider.getBus().post(new LoginEvent());

        BusProvider.getBus()
                .subscribe(this, new RxBus.Callback<LoginEvent>() {
                    @Override
                    public void onEvent(LoginEvent loginEvent) {
                        //TODO 事件处理
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public Object newP() {
        return null;
    }
}
