package com.rtyhdfh.mnzdfgdsg.uuuu.fragment;

import android.os.Bundle;

import com.rtyhdfh.mnzdfgdsg.R;
import com.rtyhdfh.mnzdfgdsg.mvp.XFragment;

public class GeiNiHuaProductFragment extends XFragment {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_geinihua;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static String oiuityj(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mxfgnxf(Object o) {

        return toDouble(o, 0);
    }

    public static double wertfs(Object o, int defaultValue) {
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

    public static long mngfxhzdfg(Object o, long defaultValue) {
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
