package com.jinyu.xiaopu.fenfujiewidget;

import android.os.CountDownTimer;
import android.widget.TextView;


import com.jinyu.xiaopu.FenFuJieApp;
import com.jinyu.xiaopu.R;


public class CountDownFenFuJieTimerUtils extends CountDownTimer {

    private TextView mTextView;

    public CountDownFenFuJieTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时时间
        mTextView.setTextColor(FenFuJieApp.getContext().getResources().getColor(R.color.text_normal_color)); //设置按钮为灰色，这时是不能点击的
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取验证码");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(FenFuJieApp.getContext().getResources().getColor(R.color.white));  //还原背景色
    }

}