package com.jiekuanzhijiasdoert.fghirtidfks.test.mvp.multi_p;

import com.jiekuanzhijiasdoert.fghirtidfks.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
