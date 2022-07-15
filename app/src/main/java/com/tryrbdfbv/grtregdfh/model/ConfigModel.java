package com.tryrbdfbv.grtregdfh.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.annotations.SerializedName;

public class ConfigModel {

    @SerializedName("COMPANY_NAME")
    private String companyName;

    @SerializedName("IS_SELECT_LOGIN")
    private String isSelectLogin;

    @SerializedName("IS_CODE_LOGIN")
    private String isCodeLogin;

    @SerializedName("APP_MAIL")
    private String appMail;

    @SerializedName("APP_NAME")
    private String appName;

    private void resetViewRl(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @SerializedName("IS_ONEBUTTON_LOGIN")
    private String isOneButtonLogin;

    @SerializedName("COMPANY_ADDRESS")
    private String companyAddress;

    @SerializedName("DOMAIN_NAME")
    private String domainName;

    public String getIsSelectLogin() {
        return isSelectLogin;
    }

    public void setIsSelectLogin(String isSelectLogin) {
        this.isSelectLogin = isSelectLogin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsCodeLogin() {
        return isCodeLogin;
    }

    public void setIsCodeLogin(String isCodeLogin) {
        this.isCodeLogin = isCodeLogin;
    }

    private void bbzdrtzsdfg(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void qfzsdgdret(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public String getAppMail() {
        return appMail;
    }

    public void setAppMail(String appMail) {
        this.appMail = appMail;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIsOneButtonLogin() {
        return isOneButtonLogin;
    }

    public void setIsOneButtonLogin(String isOneButtonLogin) {
        this.isOneButtonLogin = isOneButtonLogin;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    private void ettsgdfh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void bbdtgr(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
