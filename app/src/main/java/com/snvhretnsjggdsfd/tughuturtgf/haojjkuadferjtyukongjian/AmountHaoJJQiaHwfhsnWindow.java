package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyukongjian;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.snvhretnsjggdsfd.tughuturtgf.R;

import razerdp.basepopup.BasePopupWindow;

public class AmountHaoJJQiaHwfhsnWindow extends BasePopupWindow {

    TextView jine_tv_1, jine_tv_2, jine_tv_3, jine_tv_4, jine_tv_5;

    private OnItemClieckListener onItemClieckListener;

    public AmountHaoJJQiaHwfhsnWindow(Context context) {
        super(context);
        setContentView(R.layout.hao_jjqian_dfjr_uert_hw_window_amount);
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
