package com.xg.qdk.u;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class StatusBarView extends View {
    protected ArrayList<Integer> lineHeightList = new ArrayList<>();//保存每行的行高
    protected ArrayList<ArrayList<View>> viewList = new ArrayList<>();//保存每行的view

    public StatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarView(Context context) {
        super(context);
    }

    public ArrayList<View> getLineViews(int number) {
        if (number < 0 || number > 44) {
            return new ArrayList<>();
        }
        return viewList.get(number);
    }

    public static class MyLayoutParams extends ViewGroup.MarginLayoutParams {
        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyLayoutParams(ViewGroup.LayoutParams lp) {
            super(lp);
        }
    }
}
