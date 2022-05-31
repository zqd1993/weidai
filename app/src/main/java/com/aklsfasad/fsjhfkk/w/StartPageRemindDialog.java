package com.aklsfasad.fsjhfkk.w;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.u.OpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageRemindDialog extends Dialog {

    @BindView(R.id.two_btn)
    TextView twoBtn;
    @BindView(R.id.tv_txt)
    ClickTextView tvTxt;
    @BindView(R.id.one_btn)
    TextView oneBtn;

    private OnListener onListener;

    public StartPageRemindDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_page);
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

        tvTxt.setText(OpenUtil.createSpanTexts(), position -> {
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
