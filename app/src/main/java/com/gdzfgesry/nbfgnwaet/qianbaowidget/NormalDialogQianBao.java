package com.gdzfgesry.nbfgnwaet.qianbaowidget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdzfgesry.nbfgnwaet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalDialogQianBao extends Dialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_left)
    TextView tvCancel;
    @BindView(R.id.tv_right)
    TextView tvConfirm;
    @BindView(R.id.v_dividing)
    View dividing;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.only_btn_ll)
    View onlyBtnLl;
    @BindView(R.id.two_btn_ll)
    View twoBtnLl;
    @BindView(R.id.sure_btn)
    TextView sureBtn;

    private String title, content;
    private View.OnClickListener leftListener, rightListener;
    private boolean hideLeftBtn = false, showOnlyBtn;
    private int imgRes;

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

    private String cancel, confirm;

    public NormalDialogQianBao(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qian_bao_normal);
        ButterKnife.bind(this);

        tvTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        tvContent.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        ivImg.setVisibility(imgRes == 0 ? View.GONE : View.VISIBLE);
        if (imgRes != 0) {
            ivImg.setImageResource(imgRes);
        }

        tvTitle.setText(title);
        tvContent.setText(content);

        if (leftListener != null) {
            tvCancel.setOnClickListener(leftListener);
        } else {
            tvCancel.setOnClickListener(v -> dismiss());
        }
        if (showOnlyBtn){
            twoBtnLl.setVisibility(View.GONE);
            onlyBtnLl.setVisibility(View.VISIBLE);
        }

        tvConfirm.setOnClickListener(rightListener);

        tvCancel.setText(cancel);
        tvConfirm.setText(confirm);
        sureBtn.setOnClickListener(v -> {
            dismiss();
        });

        if (hideLeftBtn) {
            tvCancel.setVisibility(View.GONE);
            dividing.setVisibility(View.GONE);
        }
    }

    public static String kdryhfjhj(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double zwtyxfgj(Object o) {

        return toDouble(o, 0);
    }

    public static double ytuhxfgjh(Object o, int defaultValue) {
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

    public static long kzrefgj(Object o, long defaultValue) {
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
    public void show() {
        super.show();
        tvTitle.setText(title);
        tvContent.setText(content);
    }

    public NormalDialogQianBao showOnlyBtn(){
        showOnlyBtn = true;
        return this;
    }

    public static String jjhsrtyfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double zhzrtyhg(Object o) {

        return toDouble(o, 0);
    }

    public static double rrtaetdfh(Object o, int defaultValue) {
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

    public static long jjrthfgjxj(Object o, long defaultValue) {
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

    public NormalDialogQianBao hideLeftBtn() {
        hideLeftBtn = true;
        return this;
    }

    public NormalDialogQianBao setTitle(String title) {
        this.title = title;
        return this;
    }

    public NormalDialogQianBao setImg(int imgRes) {
        this.imgRes = imgRes;
        return this;
    }

    public static String mxfjryxfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double eryxnxft(Object o) {

        return toDouble(o, 0);
    }

    public static double heryfxgj(Object o, int defaultValue) {
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

    public static long tuxgnxftyu(Object o, long defaultValue) {
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

    public NormalDialogQianBao setContent(String content) {
        this.content = content;
        return this;
    }

    public NormalDialogQianBao setLeftListener(View.OnClickListener listener) {
        this.leftListener = listener;
        return this;
    }

    public static String ouisrthj(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double tusrfxghj(Object o) {

        return toDouble(o, 0);
    }

    public static double zbryjhs(Object o, int defaultValue) {
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

    public static long fghzreyxn(Object o, long defaultValue) {
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

    public NormalDialogQianBao setRightListener(View.OnClickListener listener) {
        this.rightListener = listener;
        return this;
    }

    public NormalDialogQianBao setCancelText(String tvCancel) {
        cancel = tvCancel;
//        this.tvCancel.setText(tvCancel);
        return this;
    }

    public static String qwerxfgju(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double retxhxty(Object o) {

        return toDouble(o, 0);
    }

    public static double uurtyzdfh(Object o, int defaultValue) {
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

    public static long mdusrye(Object o, long defaultValue) {
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

    public NormalDialogQianBao setConfirmText(String tvConfirm) {
        confirm = tvConfirm;
//        this.tvConfirm.setText(tvConfirm);
        return this;
    }

    public String getContent() {
        return TextUtils.isEmpty(content) ? "" : content;
    }

}
