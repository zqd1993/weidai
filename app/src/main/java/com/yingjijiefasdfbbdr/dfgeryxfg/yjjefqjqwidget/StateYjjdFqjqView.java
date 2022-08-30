package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateYjjdFqjqView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateYjjdFqjqView(Context context) {
        super(context);
        setupView(context);
    }

    public StateYjjdFqjqView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateYjjdFqjqView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_yjjdfqjq_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
