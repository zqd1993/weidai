package com.octone.pelicnenwo.test.mvp.no_p;

import android.os.Bundle;

import com.octone.pelicnenwo.mvp.XActivity;

/**
 * Created by wanglei on 2017/1/30.
 */

public class NoPActivity extends XActivity {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public Object newP() {
        return null;
    }
}
