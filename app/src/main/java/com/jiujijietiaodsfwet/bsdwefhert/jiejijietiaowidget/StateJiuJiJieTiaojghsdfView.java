package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaowidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateJiuJiJieTiaojghsdfView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateJiuJiJieTiaojghsdfView(Context context) {
        super(context);
        setupView(context);
    }

    public StateJiuJiJieTiaojghsdfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateJiuJiJieTiaojghsdfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_jiu_ji_jie_tiao_boss_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
