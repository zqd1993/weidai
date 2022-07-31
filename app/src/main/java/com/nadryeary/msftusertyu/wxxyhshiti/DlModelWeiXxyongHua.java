package com.nadryeary.msftusertyu.wxxyhshiti;

public class DlModelWeiXxyongHua {
    private String phone;

    private String device;

    private int mobileType;


    private String ip;

    private int userLevel;

    private int state;

    private String crtTime;

    private String updTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }
    public int getMobileType() {
        return mobileType;
    }

    public void setMobileType(int mobileType) {
        this.mobileType = mobileType;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getState() {
        return state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCrtTime() {
        return crtTime;
    }
    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }


}
