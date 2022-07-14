package com.gdzfgesry.nbfgnwaet.qianbaowidget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gdzfgesry.nbfgnwaet.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeDialogQianBao extends Dialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    QianBaoSpanTextView tvContent;
    @BindView(R.id.top_btn)
    TextView topBtn;
    @BindView(R.id.bottom_btn)
    TextView bottomBtn;

    private OnClickedListener onClickedListener;
    private String title, content;

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

    public WelcomeDialogQianBao(@NonNull Context context, String title) {
        super(context, R.style.tran_dialog);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_welcome_qian_bao);
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

    public static String mytuifjhf(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double qtytxj(Object o) {

        return toDouble(o, 0);
    }

    public static double oiofytujx(Object o, int defaultValue) {
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

    public static long zretfxh(Object o, long defaultValue) {
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

    private List<QianBaoSpanTextView.BaseSpanModel> createSpanTexts(){
        List<QianBaoSpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        QianBaoSpanTextView.ClickSpanModel spanModel = new QianBaoSpanTextView.ClickSpanModel();
        QianBaoSpanTextView.TextSpanModel textSpanModel = new QianBaoSpanTextView.TextSpanModel();
        textSpanModel.setContent("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。\n\n" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new QianBaoSpanTextView.TextSpanModel();
        textSpanModel.setContent("和");
        spanModels.add(textSpanModel);

        spanModel = new QianBaoSpanTextView.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new QianBaoSpanTextView.TextSpanModel();
        textSpanModel.setContent("，才能继续使用我们的服务哦。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    public static String bzdtgrtuyfgj(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double kytugfhj(Object o) {

        return toDouble(o, 0);
    }

    public static double erxfjhytu(Object o, int defaultValue) {
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

    public static long rtetxjhx(Object o, long defaultValue) {
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

    public static String nzrxfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ldtihzrh(Object o) {

        return toDouble(o, 0);
    }

    public static double ertrthxfgj(Object o, int defaultValue) {
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

    public static long urtxfgh(Object o, long defaultValue) {
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

}
