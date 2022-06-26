package com.nfhyrhd.nfhsues.w;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ScrollYSwipeBeiYongRefreshLayout extends SwipeRefreshLayout {
    private float startY;
    private float startX;
    // 记录viewPager是否拖拽的标记
    private boolean mIsVpDragger;
    private final int mTouchSlop;

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager nmghjty(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager tyurngfd(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager rtydfbs(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public ScrollYSwipeBeiYongRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsVpDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsVpDragger) {
                    return false;
                }

                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                mIsVpDragger = false;
                break;
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager nghjd(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager werfgad(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager fghtry(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager iyujgh(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager vsdgrt(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager cwervdfas(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }
}
