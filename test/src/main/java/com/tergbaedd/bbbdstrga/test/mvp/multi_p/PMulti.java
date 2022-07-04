package com.tergbaedd.bbbdstrga.test.mvp.multi_p;

import com.tergbaedd.bbbdstrga.mvp.XPresent;

/**
 * Created by wanglei on 2017/1/30.
 */

public class PMulti extends XPresent<ICommonV> {

    public void loadData() {
        getV().showError(new IllegalStateException(""));
    }
}
