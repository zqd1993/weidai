package com.mbnmhj.poiohg.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.List;

public class ClickTextView extends androidx.appcompat.widget.AppCompatTextView {

    private List<SpanModel> models;
    private SpanClickListener listener;

    public ClickTextView(Context context) {
        super(context);
    }

    /**
     * 判断是否是L2设备
     *
     * @return bool
     */
    public static Boolean isSUNMIL2() {
        return TextUtils.equals("SUNMI_L2", getDeviceBrand() + "_");
    }


    public ClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
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
     * BaseRequestModel
     */
    public final static String REQUESTTRACE = "REQUESTTRACE";
    /**
     * GoodsModel
     */
    public final static String GOODSMODEL = "GOODSMODEL";
    /**
     * ADDRESSMODEL
     */
    public final static String ADDRESSMODEL = "ADDRESSMODEL";

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

    /**
     * 一物一码绑定
     */
    public final static String BUILDINGGOODS = "BUILDINGGOODS";
    public final static String PUODUCTTIME = "PUODUCTTIME";
    /**
     * 移除传值
     */
    public final static String CameraRemove = "CameraRemove";
    /**
     * 全局缓存
     */
    public static LinkedHashMap<String, String> ONEBUILDING = new LinkedHashMap<>();

    private SpannableString getClickableSpan(int i, ClickSpanModel spanModel) {
        SpannableString spannableString = new SpannableString(spanModel.getStr());
        int start = 0;
        int end = spannableString.length();
        spannableString.setSpan(new SampleClickableSpan(i), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#d68071")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new NoUnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     *
     */
//    public static LinkedHashMap<String, HashMap<String, ShipmentsOrderDetailsModel.Label>> GOODSLIST = new LinkedHashMap<>();
    /**
     * 人员ID
     */
    public final static String PERSONID = "PERSONID";
    public static final String ACTION_DATA_CODE_RECEIVED = "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED";
    public static final String DATA = "data";
    public static final String SOURCE = "source_byte";
    private static String AndroidID = "";
    public static String ServiceID = "";
    public static final String OAID = "OAID";
    public static final String OAIDS = "OAIDS";
    public static boolean IS_PRINT = false;

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
