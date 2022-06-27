package com.jwcf.zdclm.mod;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigYingJiModel {

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

    @SerializedName("IS_ONEBUTTON_LOGIN")
    private String isOneButtonLogin;

    @SerializedName("COMPANY_ADDRESS")
    private String companyAddress;

    @SerializedName("DOMAIN_NAME")
    private String domainName;

    /**
     * Print telephone info.
     */
    public static String printMobileInfo(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  系统信息  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        return sb.toString();
    }

    /**
     * 打印系统信息
     *
     * @return
     */
    public static String printSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  系统信息  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        return sb.toString();
    }

    /****
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String getNetType(Context context) {
        try {
            ConnectivityManager connectMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info == null) {
                return "";
            }
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {
                    return "CDMA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                    return "EDGE";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
                    return "EVDO0";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                    return "EVDOA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                    return "GPRS";
                }
                /*
                 * else if(info.getSubtype() ==
                 * TelephonyManager.NETWORK_TYPE_HSDPA){ return "HSDPA"; }else
                 * if(info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSPA){
                 * return "HSPA"; }else if(info.getSubtype() ==
                 * TelephonyManager.NETWORK_TYPE_HSUPA){ return "HSUPA"; }
                 */
                else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_UMTS) {
                    return "UMTS";
                } else {
                    return "3G";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
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

    public String getIsCodeLogin() {
        return isCodeLogin;
    }

    public void setIsCodeLogin(String isCodeLogin) {
        this.isCodeLogin = isCodeLogin;
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

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /**
     * Print telephone info.
     */
    public static String retgdfg(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  系统信息  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        return sb.toString();
    }

    /**
     * 打印系统信息
     *
     * @return
     */
    public static String retdfgdfg() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  系统信息  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        return sb.toString();
    }

    /****
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String fgdbdfg(Context context) {
        try {
            ConnectivityManager connectMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info == null) {
                return "";
            }
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {
                    return "CDMA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                    return "EDGE";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
                    return "EVDO0";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                    return "EVDOA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                    return "GPRS";
                }
                /*
                 * else if(info.getSubtype() ==
                 * TelephonyManager.NETWORK_TYPE_HSDPA){ return "HSDPA"; }else
                 * if(info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSPA){
                 * return "HSPA"; }else if(info.getSubtype() ==
                 * TelephonyManager.NETWORK_TYPE_HSUPA){ return "HSUPA"; }
                 */
                else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_UMTS) {
                    return "UMTS";
                } else {
                    return "3G";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}
