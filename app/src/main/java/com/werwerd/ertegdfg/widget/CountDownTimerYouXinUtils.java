package com.werwerd.ertegdfg.widget;

import android.os.CountDownTimer;
import android.widget.TextView;


import com.werwerd.ertegdfg.YouXinApp;
import com.werwerd.ertegdfg.R;


public class CountDownTimerYouXinUtils extends CountDownTimer {

    private TextView mTextView;

    @Override
    public void onFinish() {
        mTextView.setText("重新获取验证码");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(YouXinApp.getContext().getResources().getColor(R.color.color_2974b8));  //还原背景色
    }

    public CountDownTimerYouXinUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时时间
        mTextView.setTextColor(YouXinApp.getContext().getResources().getColor(R.color.colorPrimary)); //设置按钮为灰色，这时是不能点击的
    }


}
