package com.dfgdervhw.erterqweq.test.mvp.multi_p;

import android.os.Bundle;

import com.dfgdervhw.erterqweq.mvp.XActivity;

/**
 * Created by wanglei on 2017/1/30.
 */

public class BActivity extends XActivity<PMulti> implements ICommonV {
    @Override
    public void initData(Bundle savedInstanceState) {
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public PMulti newP() {
        return new PMulti();
    }


    @Override
    public void showError(Exception e) {

    }
}
