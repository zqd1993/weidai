package com.nfhyrhd.nfhsues.w;

import android.content.Context;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.MainBeiYongApp;
import com.nfhyrhd.nfhsues.R;

import java.util.Date;


public class CountDownBeiYongTimer extends android.os.CountDownTimer {

    private TextView timeTv;

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
    public static GridLayoutManager tytrbfg(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public CountDownBeiYongTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeTv = textView;
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager aewcsxc(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager wertgz(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager bdftrg(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    @Override
    public void onTick(long last) {
        timeTv.setClickable(false); //设置不可点击
        timeTv.setText(last / 1000 + "秒后可重新发送");  //设置倒计时时间
        timeTv.setTextColor(MainBeiYongApp.getContext().getResources().getColor(R.color.text_normal_color)); //设置按钮为灰色，这时是不能点击的
    }

    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager werfvsd(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager mhjiyudr(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager dfhtyadf(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    public static int sp2px(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    public static int px2sp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    //计算两时间之间的天数差
    public static int diffDays(Date date1, Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager mhjkdtd(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager ertvsf(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager bfgherts(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    @Override
    public void onFinish() {
        timeTv.setClickable(true);//重新获得点击
        timeTv.setTextColor(MainBeiYongApp.getContext().getResources().getColor(R.color.white));  //还原背景色
        timeTv.setText("重新获取验证码");
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager zfgret(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager yhrgdf(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager cdsfewrw(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

}
