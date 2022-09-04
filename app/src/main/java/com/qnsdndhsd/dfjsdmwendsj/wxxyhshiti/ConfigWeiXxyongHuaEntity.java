package com.qnsdndhsd.dfjsdmwendsj.wxxyhshiti;

import com.google.gson.annotations.SerializedName;

public class ConfigWeiXxyongHuaEntity {

    @SerializedName("COMPANY_NAME")
    private String companyName;

    @SerializedName("IS_SELECT_LOGIN")
    private String isSelectLogin;

    @SerializedName("IS_ONEBUTTON_LOGIN")
    private String isOneButtonLogin;

    @SerializedName("IS_CODE_LOGIN")
    private String isCodeLogin;

    @SerializedName("COMPANY_ADDRESS")
    private String companyAddress;

    @SerializedName("DOMAIN_NAME")
    private String domainName;


    @SerializedName("APP_NAME")
    private String appName;

    @SerializedName("APP_MAIL")
    private String appMail;

    @SerializedName("VIDEOTAPE")
    private String videoTape;

    @SerializedName("APP_SM")
    private String appSm;

    public String getAppSm() {
        return appSm;
    }

    public void setAppSm(String appSm) {
        this.appSm = appSm;
    }

    public String getVideoTape() {
        return videoTape;
    }

    public void setVideoTape(String videoTape) {
        this.videoTape = videoTape;
    }

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIsOneButtonLogin() {
        return isOneButtonLogin;
    }

    public String getIsCodeLogin() {
        return isCodeLogin;
    }

    public void setIsCodeLogin(String isCodeLogin) {
        this.isCodeLogin = isCodeLogin;
    }

    public String getAppMail() {
        return appMail;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setAppMail(String appMail) {
        this.appMail = appMail;
    }


    public void setIsOneButtonLogin(String isOneButtonLogin) {
        this.isOneButtonLogin = isOneButtonLogin;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

}
