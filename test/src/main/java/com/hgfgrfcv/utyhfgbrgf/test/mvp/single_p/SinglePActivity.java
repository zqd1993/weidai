package com.hgfgrfcv.utyhfgbrgf.test.mvp.single_p;

import android.os.Bundle;

import com.hgfgrfcv.utyhfgbrgf.mvp.XActivity;

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