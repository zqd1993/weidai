package com.dlnsg.ytjwhbm.hwshanjiebeiyongkongjian;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

import com.dlnsg.ytjwhbm.HWShanJieBeiYongJinApp;
import com.dlnsg.ytjwhbm.R;

import java.util.Date;


public class CountHWShanJieBeiYongJinDownTimer extends android.os.CountDownTimer {

    private TextView timeTv;

    private static String mSDCardFolderPath;
    private static String mImgFolderPath;
    private static String mApkFolderPath;
    private static String mCacheFolderPath;
    private static String mLogFolderPath;
    //下载图片路径
    private static String mCacheFolderLoadPicPath;

    public float density;


    public CountHWShanJieBeiYongJinDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeTv = textView;
    }

    @Override
    public void onTick(long last) {
        timeTv.setClickable(false); //设置不可点击
        timeTv.setText(last / 1000 + "秒后可重新发送");  //设置倒计时时间
        timeTv.setTextColor(HWShanJieBeiYongJinApp.getContext().getResources().getColor(R.color.color_bdbdc5)); //设置按钮为灰色，这时是不能点击的
    }

    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        int density = (int) (Resources.getSystem().getDisplayMetrics().density + 0.5f);
        return (int) (0.5f + dpValue * density);
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
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return px
     */
    public static int sp2px(float spValue) {
        return (int) (0.5f + spValue * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    public static float sp2dp(float spValue) {
        float pxValue = (0.5f + spValue * Resources.getSystem().getDisplayMetrics().scaledDensity);
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void onFinish() {
        timeTv.setClickable(true);//重新获得点击
        timeTv.setTextColor(HWShanJieBeiYongJinApp.getContext().getResources().getColor(R.color.white));  //还原背景色
        timeTv.setText("重新获取验证码");
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return sp
     */
    public static int px2sp(float pxValue) {
        return (int) (0.5f + pxValue / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

}
