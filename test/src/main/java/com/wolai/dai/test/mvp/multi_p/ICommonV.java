package com.wolai.dai.test.mvp.multi_p;

import com.wolai.dai.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
