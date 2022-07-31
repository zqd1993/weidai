package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateDiXiDaiKuanView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateDiXiDaiKuanView(Context context) {
        super(context);
        setupView(context);
    }

    public StateDiXiDaiKuanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateDiXiDaiKuanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_di_xi_dai_kuan_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
