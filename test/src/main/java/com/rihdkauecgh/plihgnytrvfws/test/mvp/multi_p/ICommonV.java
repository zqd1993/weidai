package com.rihdkauecgh.plihgnytrvfws.test.mvp.multi_p;

import com.rihdkauecgh.plihgnytrvfws.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
