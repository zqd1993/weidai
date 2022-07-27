package com.octone.pelicnenwo.test.rxbus;

import com.octone.pelicnenwo.event.IBus;

/**
 * Created by wanglei on 2017/1/30.
 */

public class LoginEvent extends IBus.AbsEvent {

    @Override
    public int getTag() {
        return 0;
    }
}
