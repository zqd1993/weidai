package com.qwerxcvdf.ytubvcbfg.test.mvp.multi_p;

import com.qwerxcvdf.ytubvcbfg.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
