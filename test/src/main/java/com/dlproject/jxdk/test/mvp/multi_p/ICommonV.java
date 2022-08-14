package com.dlproject.jxdk.test.mvp.multi_p;

import com.dlproject.jxdk.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
