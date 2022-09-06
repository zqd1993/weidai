package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmw;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdOpenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageRemindDialogLingHuoJieSvsdFd extends Dialog {

    @BindView(R.id.two_btn)
    TextView twoBtn;
    @BindView(R.id.tv_txt)
    ClickTextViewLingHuoJieSvsdFd tvTxt;
    @BindView(R.id.one_btn)
    TextView oneBtn;

    private OnListener onListener;

    public StartPageRemindDialogLingHuoJieSvsdFd(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_page_ling_huo_jie_djs_urng);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        oneBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.oneBtnClicked();
            }
        });

        twoBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.twoBtnClicked();
            }
        });

        tvTxt.setText(LingHuoJieSvsdFdOpenUtil.createSpanTexts(), position -> {
            switch (position) {
                case 1:
                    if (onListener != null) {
                        onListener.zcxyClicked();
                    }
                    break;
                default:
                    if (onListener != null) {
                        onListener.ysxyClicked();
                    }
                    break;
            }
        });

    }

    @Override
    public void show() {
        super.show();
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public interface OnListener {
        void oneBtnClicked();

        void twoBtnClicked();

        void zcxyClicked();

        void ysxyClicked();
    }

}
