package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mmaeryrusu.qqzdryty.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FenQiHuanQianBeiWelcomeDialog extends Dialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    FenQiHuanQianBeiSpanTextView tvContent;
    @BindView(R.id.top_btn)
    TextView topBtn;
    @BindView(R.id.bottom_btn)
    TextView bottomBtn;

    private OnClickedListener onClickedListener;
    private String title, content;

    public FenQiHuanQianBeiWelcomeDialog(@NonNull Context context, String title) {
        super(context, R.style.tran_dialog);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_welcome_fen_qi_huan_qian);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        tvTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);

        tvTitle.setText(title);
        tvContent.setText(content);

        tvContent.setText(createSpanTexts(), position -> {
            if (position == 1){
                if (onClickedListener != null){
                    onClickedListener.registrationAgreementClicked();
                }
            } else {
                if (onClickedListener != null){
                    onClickedListener.privacyAgreementClicked();
                }
            }
        });

        topBtn.setOnClickListener(v -> {
            if (onClickedListener != null){
                onClickedListener.topBtnClicked();
            }
        });

        bottomBtn.setOnClickListener(v -> {
            if (onClickedListener != null){
                onClickedListener.bottomBtnClicked();
            }
        });

    }


    private List<FenQiHuanQianBeiSpanTextView.BaseSpanModel> createSpanTexts(){
        List<FenQiHuanQianBeiSpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        FenQiHuanQianBeiSpanTextView.ClickSpanModel spanModel = new FenQiHuanQianBeiSpanTextView.ClickSpanModel();
        FenQiHuanQianBeiSpanTextView.TextSpanModel textSpanModel = new FenQiHuanQianBeiSpanTextView.TextSpanModel();
        textSpanModel.setContent("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。\n\n" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new FenQiHuanQianBeiSpanTextView.TextSpanModel();
        textSpanModel.setContent("和");
        spanModels.add(textSpanModel);

        spanModel = new FenQiHuanQianBeiSpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new FenQiHuanQianBeiSpanTextView.TextSpanModel();
        textSpanModel.setContent("，才能继续使用我们的服务哦。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    @Override
    public void show() {
        super.show();
    }

    public void setOnClickedListener(OnClickedListener onClickedListener){
        this.onClickedListener = onClickedListener;
    }

    public interface OnClickedListener{
        void topBtnClicked();
        void bottomBtnClicked();
        void registrationAgreementClicked();
        void privacyAgreementClicked();
    }

}
