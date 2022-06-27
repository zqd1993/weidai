package com.lpsydsnl.jtxqchbb.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DlModelMeiJie {
    private String phone;

    private String device;

    private int mobileType;

    private int state;

    private String crtTime;

    private String updTime;

    private String ip;

    private int userLevel;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getMobileType() {
        return mobileType;
    }

    public void setMobileType(int mobileType) {
        this.mobileType = mobileType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean mjhjtyj(Context mContext) {
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
    public static boolean bfghrt(Context mContext, boolean distinguish, int type) {
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

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
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

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean werwdfds(Context mContext) {
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
    public static boolean nghjtyh(Context mContext, boolean distinguish, int type) {
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
}
