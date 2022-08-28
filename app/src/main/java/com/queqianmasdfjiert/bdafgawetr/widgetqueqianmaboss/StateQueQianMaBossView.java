package com.queqianmasdfjiert.bdafgawetr.widgetqueqianmaboss;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateQueQianMaBossView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateQueQianMaBossView(Context context) {
        super(context);
        setupView(context);
    }

    public StateQueQianMaBossView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateQueQianMaBossView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_que_qian_ma_boss_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
