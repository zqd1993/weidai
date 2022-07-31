package com.mmaeryrusu.qqzdryty.test.mvp.multi_p;

import com.mmaeryrusu.qqzdryty.mvp.XPresent;

/**
 * Created by wanglei on 2017/1/30.
 */

public class PMulti extends XPresent<ICommonV> {

    public void loadData() {
        getV().showError(new IllegalStateException(""));
    }
}
