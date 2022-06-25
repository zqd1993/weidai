package com.xvhyrt.ghjtyu.w;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClickTextView extends androidx.appcompat.widget.AppCompatTextView {

    private List<SpanModel> models;
    private SpanClickListener listener;

    public ClickTextView(Context context) {
        super(context);
    }

    public ClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager nmghjty(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager tyurngfd(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager tytrbfg(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public static class SpanModel {

        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager csdfewr(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager yturs(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager bdfgrt(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public static class ClickSpanModel extends SpanModel {
        private int id;

        public ClickSpanModel() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    private SpannableString getClickableSpan(int i, ClickSpanModel spanModel) {
        SpannableString spannableString = new SpannableString(spanModel.getStr());
        int start = 0;
        int end = spannableString.length();
        spannableString.setSpan(new SampleClickableSpan(i), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fdbd56")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new NoUnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public void setText(List<SpanModel> models, SpanClickListener listener) {
        this.setClickable(true);
        this.models = models;
        this.listener = listener;
        this.setMovementMethod(LinkMovementMethod.getInstance());
        for (int i = 0; i < models.size(); i++) {
            SpanModel baseSpanModel = models.get(i);
            SpannableString spannableString;
            if (baseSpanModel instanceof ClickSpanModel) {
                spannableString = getClickableSpan(i, (ClickSpanModel) baseSpanModel);
            } else {
                spannableString = new SpannableString(baseSpanModel.getStr());
            }
            if (i == 0) {
                this.setText(spannableString);
            } else {
                this.append(spannableString);
            }
        }
    }

    class SampleClickableSpan extends ClickableSpan implements OnClickListener {
        private int position;

        public SampleClickableSpan(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnClickListener(position);
            }
            setText(models, listener);
        }
    }

    public class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    public interface SpanClickListener {
        void OnClickListener(int position);
    }

}
