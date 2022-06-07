package com.mbnmhj.poiohg.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.imageloader.ILoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemindDialog extends Dialog {

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

    public static String personId = "";

    public static String getPersonId() {
        return TextUtils.isEmpty(personId) ? personId : personId;
    }

    public static final int SHIPMENT_SEND_THE_GOODS = 40;             //出货:待收货
    public static final int SHIPMENT_HAS_BEEN_COMPLETED = 42;         //出货:已完成
    public static final int SHIPMENT_IN_AND_OUT = 43;                 //出货:已完成(有出入)
    public static final int SHIPMENT_HAD_WITHDRAWN = 41;              //出货:已撤销
    public static final int RETURN_GOODS_HAS_BEEN_COMPLETED = 24;     //退货:已完成
    public static final int RETURN_GOODS_HAD_WITHDRAWN = 25;          //退货:已撤销
    public static final int PUT_STORAGE_HAS_BEEN_COMPLETED = 44;      //入库:已完成
    public static final int PUT_STORAGE_HAD_WITHDRAWN = 45;           //入库:已撤销

    private OnButtonClickListener onclickListener;
    private String title, content;
    private boolean showOnlyBtn;

    private String cancel, confirm;

    public RemindDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
    }

    public static final int ORDER_BORROWING = 1;           //借货
    public static final int ORDER_RETURN = 2;           //还货
    public static final int ORDER_RETURNS = 3;           //退货
    public static final int ORDER_OUTBOUND = 4;           //出入库
    public static final int ORDER_SALES = 5;           //销售
    public static final int ORDER_USE = 6;           //领用

    //V_1.1
    public static final int ORDER_TYPE_UNRIGHT = 4;   //出货:已完成(有出入)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
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
     * 判断是否需要隐藏输入法
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


    @Override
    public void show() {
        super.show();
        tvTitle.setText(title);
        tvContent.setText(content);
    }

    public RemindDialog showOnlyBtn(){
        showOnlyBtn = true;
        return this;
    }

    public RemindDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param v
     */
    public static void hideKeyboard(View v) {

    }

    /**
     * 自动关闭软键盘
     *
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public RemindDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public RemindDialog setCancelText(String tvCancel) {
        cancel = tvCancel;
        return this;
    }

    public RemindDialog setConfirmText(String tvConfirm) {
        confirm = tvConfirm;
        return this;
    }

    //默认头像
    public static ILoader.Options defaultHead = new ILoader.Options(ILoader.Options.RES_NONE, R.mipmap.app_logo);
    //默认无图像
    public static ILoader.Options defaultNoneImage = new ILoader.Options(ILoader.Options.RES_NONE, R.mipmap.app_logo);
    //默认无图像
    public static ILoader.Options defaultNoneLogo = new ILoader.Options(ILoader.Options.RES_NONE, R.mipmap.app_logo);
    //默认无图像
    public static ILoader.Options defaultNoneHeader = new ILoader.Options(ILoader.Options.RES_NONE, R.mipmap.app_logo);

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
