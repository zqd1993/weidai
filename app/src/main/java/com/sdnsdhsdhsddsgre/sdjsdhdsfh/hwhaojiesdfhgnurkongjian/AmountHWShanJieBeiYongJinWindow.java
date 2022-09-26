package com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurkongjian;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.sdnsdhsdhsddsgre.sdjsdhdsfh.R;

import razerdp.basepopup.BasePopupWindow;

public class AmountHWShanJieBeiYongJinWindow extends BasePopupWindow {

    TextView jine_tv_1, jine_tv_2, jine_tv_3, jine_tv_4, jine_tv_5;

    private OnItemClieckListener onItemClieckListener;

    public AmountHWShanJieBeiYongJinWindow(Context context) {
        super(context);
        setContentView(R.layout.hw_shan_jie_bei_yong_jie_window_amount);
        jine_tv_1 = findViewById(R.id.jine_tv_1);
        jine_tv_2 = findViewById(R.id.jine_tv_2);
        jine_tv_3 = findViewById(R.id.jine_tv_3);
        jine_tv_4 = findViewById(R.id.jine_tv_4);
        jine_tv_5 = findViewById(R.id.jine_tv_5);
        setClicked(jine_tv_1);
        setClicked(jine_tv_2);
        setClicked(jine_tv_3);
        setClicked(jine_tv_4);
        setClicked(jine_tv_5);
        setBackground(Color.TRANSPARENT);
    }

    private void setClicked(TextView textView){
        textView.setOnClickListener(v -> {
            if (onItemClieckListener != null){
                onItemClieckListener.cliecked(textView.getText().toString());
            }
            dismiss();
        });
    }

    public void setOnItemClieckListener(OnItemClieckListener onItemClieckListener){
        this.onItemClieckListener = onItemClieckListener;
    }

    public interface OnItemClieckListener{
        void cliecked(String text);
    }

}
