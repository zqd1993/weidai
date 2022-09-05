package com.ruyijiekuandfwetdg.nnrdtydfgsd.widgetyijiekuandfwetr;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class RuYiJieKuanAdgFsdfStateView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public RuYiJieKuanAdgFsdfStateView(Context context) {
        super(context);
        setupView(context);
    }

    public RuYiJieKuanAdgFsdfStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public RuYiJieKuanAdgFsdfStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_state_ru_yi_jie_kuan_dfs_wetg, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
