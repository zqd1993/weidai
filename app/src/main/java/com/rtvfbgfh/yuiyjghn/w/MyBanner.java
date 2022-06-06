package com.rtvfbgfh.yuiyjghn.w;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.youth.banner.Banner;

public class MyBanner extends Banner {
    // 记录触摸的位置（主要用于解决事件冲突问题）
    private float mStartX, mStartY;
    // 滑动距离范围
    private int mTouchSlop;
    // 记录viewpager2是否被拖动
    private boolean mIsViewPager2Drag;
    public MyBanner(Context context) {
        super(context);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() / 2;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        getParent().requestDisallowInterceptTouchEvent(false);  //设置不拦截
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                float distanceX = Math.abs(endX - mStartX);
                float distanceY = Math.abs(endY - mStartY);
                mIsViewPager2Drag = distanceX > mTouchSlop && distanceX > distanceY;
                getParent().requestDisallowInterceptTouchEvent(mIsViewPager2Drag);
                break;
        }

        return false;
    }
}
