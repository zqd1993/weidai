package com.srysfghsrty.mkdtyusaert.wanrongxinyongkawidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateWanRongXinYongKaView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public StateWanRongXinYongKaView(Context context) {
        super(context);
        setupView(context);
    }

    public StateWanRongXinYongKaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateWanRongXinYongKaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_wan_rong_xin_yong_ka_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
