package com.qznagsdnfdkdjdf.cvpdfms.test.mvp.multi_p;

import com.qznagsdnfdkdjdf.cvpdfms.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
