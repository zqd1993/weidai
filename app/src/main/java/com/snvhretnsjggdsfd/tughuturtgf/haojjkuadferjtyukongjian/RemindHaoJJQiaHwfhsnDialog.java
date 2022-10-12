package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyukongjian;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.snvhretnsjggdsfd.tughuturtgf.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemindHaoJJQiaHwfhsnDialog extends Dialog {

    @BindView(R.id.v_dividing)
    View dividing;
    @BindView(R.id.only_btn_ll)
    View onlyBtnLl;
    @BindView(R.id.two_btn_ll)
    View twoBtnLl;
    @BindView(R.id.sure_btn)
    TextView sureBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvConfirm;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_left)
    TextView tvCancel;

    private OnButtonClickListener onclickListener;
    private String title, content;
    private boolean showOnlyBtn;

    private String cancel, confirm;

    public RemindHaoJJQiaHwfhsnDialog(@NonNull Context context) {
        super(context, R.style.anim_dialog);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 像素
     * @return 虚拟像素
     */
    public static float px2dp(int pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hao_jjqian_dfjr_uert_hw_dialog_normal);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        tvContent.setText(content);
        tvTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        tvContent.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        if (onclickListener != null){
            tvCancel.setOnClickListener(v -> {onclickListener.onCancelClicked();});
            tvConfirm.setOnClickListener(v -> {onclickListener.onSureClicked();});
        }
        if (showOnlyBtn){
            twoBtnLl.setVisibility(View.GONE);
            onlyBtnLl.setVisibility(View.VISIBLE);
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

    public RemindHaoJJQiaHwfhsnDialog showOnlyBtn(){
        showOnlyBtn = true;
        return this;
    }

    public RemindHaoJJQiaHwfhsnDialog setTitle(String title) {
        this.title = title;
        return this;
    }


    public RemindHaoJJQiaHwfhsnDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public RemindHaoJJQiaHwfhsnDialog setCancelText(String tvCancel) {
        cancel = tvCancel;
        return this;
    }

    public RemindHaoJJQiaHwfhsnDialog setConfirmText(String tvConfirm) {
        confirm = tvConfirm;
        return this;
    }

    public String getContent() {
        return TextUtils.isEmpty(content) ? "" : content;
    }

    public void setOnButtonClickListener(OnButtonClickListener onclickListener){
        this.onclickListener = onclickListener;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public int dip2px(float dpValue) {
        return (int) (0.5f + dpValue);
    }

    public interface OnButtonClickListener{
        void onSureClicked();
        void onCancelClicked();
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 像素
     * @return 虚拟像素
     */
    public float px2dip(int pxValue) {
        return (pxValue);
    }


}