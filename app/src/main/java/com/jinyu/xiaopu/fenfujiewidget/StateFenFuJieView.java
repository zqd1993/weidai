package com.jinyu.xiaopu.fenfujiewidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateFenFuJieView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateFenFuJieView(Context context) {
        super(context);
        setupView(context);
    }

    public StateFenFuJieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateFenFuJieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_fen_fu_jie_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
