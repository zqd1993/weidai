package com.jiekuanzhijiasdoert.fghirtidfks.widgetjiekuanzhijia;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekuanzhijiasdoert.fghirtidfks.R;
import com.jiekuanzhijiasdoert.fghirtidfks.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class JieKuanZhiJiaStateView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public JieKuanZhiJiaStateView(Context context) {
        super(context);
        setupView(context);
    }

    public JieKuanZhiJiaStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public JieKuanZhiJiaStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_state_jie_kuan_zhi_jia, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
