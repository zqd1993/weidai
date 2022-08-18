package com.asdfgaery.nnaeryaery.daikuanqianbaowidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateDaiKuanQianBaoView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateDaiKuanQianBaoView(Context context) {
        super(context);
        setupView(context);
    }

    public StateDaiKuanQianBaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateDaiKuanQianBaoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_dai_kuan_qian_bao_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
