package com.lpsydsnl.jtxqchbb.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.lpsydsnl.jtxqchbb.use.MeiJiePreferencesOpenUtil;

public class HttpMeiJieApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/tfqdk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/tfqdk/ysxy.html";
    public static String HTTP_API_URL = "45.112.206.60:7705";

    private static InterfaceUtilsMeiJie interfaceUtilsMeiJie;

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

    public static InterfaceUtilsMeiJie getInterfaceUtils() {
        if (interfaceUtilsMeiJie == null && !TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (HttpMeiJieApi.class) {
                if (interfaceUtilsMeiJie == null) {
                    interfaceUtilsMeiJie = XApi.getInstance().getRetrofit(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"), true).create(InterfaceUtilsMeiJie.class);
                }
            }
        }
        return interfaceUtilsMeiJie;
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean ewtdfsf(Context mContext) {
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
    public static boolean ngfhgf(Context mContext, boolean distinguish, int type) {
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
