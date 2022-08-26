package com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.wwww;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.R;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.kit.KnifeKit;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class StateViewGeiNiHua extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public StateViewGeiNiHua(Context context) {
        super(context);
        setupView(context);
    }

    public StateViewGeiNiHua(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateViewGeiNiHua(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    public static String ertgfsdgh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mndyt(Object o) {

        return toDouble(o, 0);
    }

    public static double wergfdsah(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long rtyghd(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_state_geinihua, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }

    public static String bxfghytry(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ewrgfdshg(Object o) {

        return toDouble(o, 0);
    }

    public static double jfghd(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long jdtrufdgh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }
}
