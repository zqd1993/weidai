package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateViewFenQiHuanQianBei extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateViewFenQiHuanQianBei(Context context) {
        super(context);
        setupView(context);
    }

    public StateViewFenQiHuanQianBei(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateViewFenQiHuanQianBei(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_fen_qi_huan_qian_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
