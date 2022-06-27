package com.lpsydsnl.jtxqchbb.view;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lpsydsnl.jtxqchbb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiJieRemindDialog extends Dialog {

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

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean isConnected(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public MeiJieRemindDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_normal_meijie);
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

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean nfgfdsgvb(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean werfsdf(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    @Override
    public void show() {
        super.show();
        tvTitle.setText(title);
        tvContent.setText(content);
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean vdsfvdsf(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean rtyhfgbs(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public MeiJieRemindDialog showOnlyBtn(){
        showOnlyBtn = true;
        return this;
    }

    public MeiJieRemindDialog setTitle(String title) {
        this.title = title;
        return this;
    }


    public MeiJieRemindDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public MeiJieRemindDialog setCancelText(String tvCancel) {
        cancel = tvCancel;
        return this;
    }

    public MeiJieRemindDialog setConfirmText(String tvConfirm) {
        confirm = tvConfirm;
        return this;
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean nfgadv(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean tybfdg(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public String getContent() {
        return TextUtils.isEmpty(content) ? "" : content;
    }

    public void setOnButtonClickListener(OnButtonClickListener onclickListener){
        this.onclickListener = onclickListener;
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean zsdcc(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean wersfsd(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public interface OnButtonClickListener{
        void onSureClicked();
        void onCancelClicked();
    }

}
