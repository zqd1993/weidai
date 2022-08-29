package com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiw;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dshqbzansdk.vpcvlsdksdhayjtop.R;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.OpenQingSongDaiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageQingSongDaiRemindDialog extends Dialog {

    @BindView(R.id.two_btn)
    TextView twoBtn;
    @BindView(R.id.tv_txt)
    ClickTextViewQingSongDai tvTxt;
    @BindView(R.id.one_btn)
    TextView oneBtn;

    private OnListener onListener;

    public StartPageQingSongDaiRemindDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_page_qing_song_dai);
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

        tvTxt.setText(OpenQingSongDaiUtil.createSpanTexts(), position -> {
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
