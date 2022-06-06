package com.bghfr.yrtweb.w;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bghfr.yrtweb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TshiDialog extends Dialog {

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
    @BindView(R.id.only_btn_ll)
    View onlyBtnLl;
    @BindView(R.id.two_btn_ll)
    View twoBtnLl;
    @BindView(R.id.sure_btn)
    TextView sureBtn;

    public static final int RC_READ_PHONE_STATE = 3001;
    public static final int RC_SETTING = 3000;
    public static final String KEY_LENTH = "lenth";
    public static final String KEY_VALUE_1 = "value_1";
    public static final String KEY_VALUE_2 = "value_2";
    public static final String KEY_VALUE_3 = "value_3";
    public static final String KEY_VALUE_4 = "value_4";
    public static final String KEY_VALUE_5 = "value_5";
    public static final String KEY_VALUE_6 = "value_6";
    public static final int REQ_CODE_1 = 4001;
    public static final int REQ_CODE_2 = 4002;
    public static final int REQ_CODE_3 = 4003;
    public static final int REQ_CODE_4 = 4004;
    public static final int REQ_CODE_5 = 5005;

    public static final int DEFULT_AREA_ID = 0;

    private OnButtonClickListener onclickListener;
    private String title, content;
    private boolean showOnlyBtn;

    private String cancel, confirm;

    public TshiDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_putong);
        ButterKnife.bind(this);

        tvTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        tvContent.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);

        tvTitle.setText(title);
        tvContent.setText(content);

        if (showOnlyBtn){
            twoBtnLl.setVisibility(View.GONE);
            onlyBtnLl.setVisibility(View.VISIBLE);
        }
        if (onclickListener != null){
            tvCancel.setOnClickListener(v -> {onclickListener.onCancelClicked();});
            tvConfirm.setOnClickListener(v -> {onclickListener.onSureClicked();});
        }
        tvCancel.setText(cancel);
        tvConfirm.setText(confirm);
        sureBtn.setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    public void show() {
        super.show();
        tvTitle.setText(title);
        tvContent.setText(content);
    }

    public TshiDialog showOnlyBtn(){
        showOnlyBtn = true;
        return this;
    }

    public TshiDialog setTitle(String title) {
        this.title = title;
        return this;
    }


    public TshiDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public TshiDialog setCancelText(String tvCancel) {
        cancel = tvCancel;
        return this;
    }
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public TshiDialog setConfirmText(String tvConfirm) {
        confirm = tvConfirm;
        return this;
    }

    public String getContent() {
        return TextUtils.isEmpty(content) ? "" : content;
    }

    public void setOnButtonClickListener(OnButtonClickListener onclickListener){
        this.onclickListener = onclickListener;
    }

    public interface OnButtonClickListener{
        void onSureClicked();
        void onCancelClicked();
    }

}
