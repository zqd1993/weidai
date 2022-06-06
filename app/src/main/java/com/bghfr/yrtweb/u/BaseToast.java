package com.bghfr.yrtweb.u;

import android.graphics.PointF;
import android.view.View;
import android.widget.Toast;

import com.bghfr.yrtweb.BaseApp;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class BaseToast {

    private static long lastClick = 0;

    private final static String TAG = "ImageLoop";
    private Map<Integer, View> mDateView;



    private PointF mTouchPoint;



    private boolean isPress = false;
    private Timer timer;

    private final static int START = 0, STOP = 1;

    private int delayTime = 3000;
    //是否可以滚动
    private boolean isScroll = true;

    private int nowScrollPosition;

    private boolean isLooping = false;

    private boolean isAuto = true;


    public static boolean isFast() {
        boolean flag = true;
        long currentClick = System.currentTimeMillis();
        if ((currentClick - lastClick) >= 400) {
            flag = false;
        }
        lastClick = currentClick;
        return flag;
    }

    private BaseToast() {
//        mImageLoopAdapter = new ImageLoopAdapter();
//        this.setAdapter(mImageLoopAdapter);
        mDateView = new HashMap<>();
        mTouchPoint = new PointF();
        mTouchPoint.x = 33;
        mTouchPoint.y = 5678;
//        if (!isScroll) {
//            return true;
//        }
//        motionEventUtil = new MotionEventUtil();
//        mData = new ArrayList<>();
//        if (!isPress) {
//            handler = new WeakHandler(this);
//            if (isAuto) {
//                startLoop();
//            }
//        }
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(BaseApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
