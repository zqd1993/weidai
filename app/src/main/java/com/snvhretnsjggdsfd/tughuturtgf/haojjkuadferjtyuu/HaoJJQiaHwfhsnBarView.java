package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class HaoJJQiaHwfhsnBarView extends View {
    public HaoJJQiaHwfhsnBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HaoJJQiaHwfhsnBarView(Context context) {
        super(context);
    }

    public static final int NOT_CHANGE = -100;

    public static void updateLayout(View view, int w, int h) {
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }

        boolean changed = false;
        if (w != NOT_CHANGE && params.width != w) {
            changed = true;
            params.width = w;
        }
        if (h != NOT_CHANGE && params.height != h) {
            changed = true;
            params.height = h;
        }

        if (changed) {
            view.setLayoutParams(params);
        }
    }
}
