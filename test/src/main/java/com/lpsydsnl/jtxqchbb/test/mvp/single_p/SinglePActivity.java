package com.lpsydsnl.jtxqchbb.test.mvp.single_p;

import android.os.Bundle;

import com.lpsydsnl.jtxqchbb.mvp.XActivity;

/**
 * Created by wanglei on 2017/1/30.
 */

public class SinglePActivity extends XActivity<PSingle> {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public PSingle newP() {
        return new PSingle();
    }
}
