package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class XianjinChaoShiStateView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public XianjinChaoShiStateView(Context context) {
        super(context);
        setupView(context);
    }

    public XianjinChaoShiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public XianjinChaoShiStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_xian_jin_chao_shi_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
