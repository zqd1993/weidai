package com.aklsfasad.fsjhfkk.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aklsfasad.fsjhfkk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalDialogHuiMin extends Dialog {

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

    private String cancel, confirm;

    public NormalDialogHuiMin(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_normal);
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

    @Override
    public void show() {
        super.show();
        tvTitle.setText(title);
        tvContent.setText(content);
    }

    public NormalDialogHuiMin showOnlyBtn(){
        showOnlyBtn = true;
        return this;
    }

    public NormalDialogHuiMin setTitle(String title) {
        this.title = title;
        return this;
    }

    public NormalDialogHuiMin setImg(int imgRes) {
        this.imgRes = imgRes;
        return this;
    }


    public NormalDialogHuiMin setContent(String content) {
        this.content = content;
        return this;
    }

    public NormalDialogHuiMin setLeftListener(View.OnClickListener listener) {
        this.leftListener = listener;
        return this;
    }

    public NormalDialogHuiMin setRightListener(View.OnClickListener listener) {
        this.rightListener = listener;
        return this;
    }

    public NormalDialogHuiMin setCancelText(String tvCancel) {
        cancel = tvCancel;
        return this;
    }

    public NormalDialogHuiMin setConfirmText(String tvConfirm) {
        confirm = tvConfirm;
        return this;
    }

    public String getContent() {
        return TextUtils.isEmpty(content) ? "" : content;
    }

}