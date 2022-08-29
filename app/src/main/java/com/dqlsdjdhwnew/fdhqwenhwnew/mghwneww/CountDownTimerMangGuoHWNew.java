package com.dqlsdjdhwnew.fdhqwenhwnew.mghwneww;

import android.content.Context;
import android.widget.TextView;

import com.dqlsdjdhwnew.fdhqwenhwnew.MainMangGuoHWNewApp;
import com.dqlsdjdhwnew.fdhqwenhwnew.R;

import java.util.Date;


public class CountDownTimerMangGuoHWNew extends android.os.CountDownTimer {

    private TextView timeTv;

    public CountDownTimerMangGuoHWNew(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeTv = textView;
    }

    @Override
    public void onTick(long last) {
        timeTv.setClickable(false); //设置不可点击
        timeTv.setText(last / 1000 + "秒后可重新发送");  //设置倒计时时间
        timeTv.setTextColor(MainMangGuoHWNewApp.getContext().getResources().getColor(R.color.text_normal_color)); //设置按钮为灰色，这时是不能点击的
    }

    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
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

    @Override
    public void onFinish() {
        timeTv.setClickable(true);//重新获得点击
        timeTv.setTextColor(MainMangGuoHWNewApp.getContext().getResources().getColor(R.color.white));  //还原背景色
        timeTv.setText("重新获取验证码");
    }

}
