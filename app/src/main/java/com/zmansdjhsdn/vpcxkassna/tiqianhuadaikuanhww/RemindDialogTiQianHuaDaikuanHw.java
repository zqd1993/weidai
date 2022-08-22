package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhww;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zmansdjhsdn.vpcxkassna.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemindDialogTiQianHuaDaikuanHw extends Dialog {

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

    private OnButtonClickListener onclickListener;
    private String title, content;
    private boolean showOnlyBtn;

    private String cancel, confirm;

    public RemindDialogTiQianHuaDaikuanHw(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ti_qian_hua_dai_kuan_hw_normal);
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

    public RemindDialogTiQianHuaDaikuanHw showOnlyBtn(){
        showOnlyBtn = true;
        return this;
    }

    public RemindDialogTiQianHuaDaikuanHw setTitle(String title) {
        this.title = title;
        return this;
    }


    public RemindDialogTiQianHuaDaikuanHw setContent(String content) {
        this.content = content;
        return this;
    }

    public RemindDialogTiQianHuaDaikuanHw setCancelText(String tvCancel) {
        cancel = tvCancel;
        return this;
    }

    public RemindDialogTiQianHuaDaikuanHw setConfirmText(String tvConfirm) {
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
