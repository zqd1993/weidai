package com.tergbaedd.bbbdstrga.test.mvp.multi_p;

import com.tergbaedd.bbbdstrga.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
