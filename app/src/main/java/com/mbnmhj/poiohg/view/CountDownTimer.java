package com.mbnmhj.poiohg.view;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.mbnmhj.poiohg.BaseApp;
import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.util.SpUtil;

import java.util.Date;
import java.util.List;


public class CountDownTimer extends android.os.CountDownTimer {

    private TextView timeTv;

    public CountDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeTv = textView;
    }

    @Override
    public void onTick(long last) {
        timeTv.setClickable(false); //设置不可点击
        timeTv.setText(last / 1000 + "秒后可重新发送");  //设置倒计时时间
        timeTv.setTextColor(BaseApp.getContext().getResources().getColor(R.color.text_color)); //设置按钮为灰色，这时是不能点击的
    }

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",  ");
            }
        }
        return sb.toString();
    }

    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static String getLabelState(int state) {
        //0 未激活 1出货、2发货、3收货、4激活、5更换、6作废
        String message = "";
        switch (state) {
            case 0:
                message = "标签为未激活状态";
                break;
            case 1:
                message = "标签为出货状态";
                break;
            case 2:
                message = "标签为发货状态";
                break;
            case 3:
                message = "标签为收货状态";
                break;
            case 4:
                message = "标签为激活状态";
                break;
            case 5:
                message = "标签为更换状态";
                break;
            case 6:
                message = "标签为作废状态";
                break;
            case 7:
                message = "标签为撤销状态";
                break;
            default:
                message = "未知状态";
                break;
        }
        return message;
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

    public static String ORGID = SpUtil.getString("orgId");

    public static String getORGID() {
        return TextUtils.isEmpty(ORGID) ? ORGID = SpUtil.getString("orgId") : ORGID;
    }

    @Override
    public void onFinish() {
        timeTv.setClickable(true);//重新获得点击
        timeTv.setTextColor(BaseApp.getContext().getResources().getColor(R.color.white));  //还原背景色
        timeTv.setText("重新获取验证码");
    }

}
