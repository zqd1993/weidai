package com.aklsfasad.fsjhfkk.w;

import android.widget.TextView;

import com.aklsfasad.fsjhfkk.MainApp;
import com.aklsfasad.fsjhfkk.R;


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
        timeTv.setTextColor(MainApp.getContext().getResources().getColor(R.color.text_normal_color)); //设置按钮为灰色，这时是不能点击的
    }

    @Override
    public void onFinish() {
        timeTv.setClickable(true);//重新获得点击
        timeTv.setTextColor(MainApp.getContext().getResources().getColor(R.color.white));  //还原背景色
        timeTv.setText("重新获取验证码");
    }

}
