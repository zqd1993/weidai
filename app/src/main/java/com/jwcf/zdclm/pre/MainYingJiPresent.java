package com.jwcf.zdclm.pre;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jwcf.zdclm.mod.YingJiBaseRespModel;
import com.jwcf.zdclm.mod.YingJiLoginRespModel;
import com.jwcf.zdclm.ui.HomePageActivityYingJi;
import com.jwcf.zdclm.utils.YingJiSharedPreferencesUtilis;
import com.jwcf.zdclm.utils.StaticYingJiUtil;
import com.jwcf.zdclm.mvp.XPresent;
import com.jwcf.zdclm.net.ApiYingJi;
import com.jwcf.zdclm.net.NetError;
import com.jwcf.zdclm.net.XApi;
import com.jwcf.zdclm.net.ApiSubscriber;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainYingJiPresent extends XPresent<HomePageActivityYingJi> {

    private String phone, ip;

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

    public void login() {
        if (!TextUtils.isEmpty(YingJiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            phone = YingJiSharedPreferencesUtilis.getStringFromPref("phone");
            ip = YingJiSharedPreferencesUtilis.getStringFromPref("ip");
            ApiYingJi.getGankService().logins(phone, ip)
                    .compose(XApi.<YingJiBaseRespModel<YingJiLoginRespModel>>getApiTransformer())
                    .compose(XApi.<YingJiBaseRespModel<YingJiLoginRespModel>>getScheduler())
                    .compose(getV().<YingJiBaseRespModel<YingJiLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YingJiBaseRespModel<YingJiLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticYingJiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YingJiBaseRespModel<YingJiLoginRespModel> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
        }
    }

    /**
     * Print telephone info.
     */
    public static String ertsdfg(Context context) {
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
    public static String mghfgb() {
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
    public static String trydfhgf(Context context) {
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
