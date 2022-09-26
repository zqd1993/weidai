package com.mbnmhj.poiohg.net;

import android.text.TextUtils;

import com.mbnmhj.poiohg.util.SpUtil;

import java.util.regex.Pattern;

public class NetApi {
    public static final String HTTP_API_URL = "http://121.41.122.164:7758";
    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/xefqd/zcxy.html";
    public static final String YSXY= "https://xyssml.yiqian888.xyz/profile/xefqd/ysxy.html";

    private static InterfaceObject interfaceObject;

    /** 正则表达式：以0或正整数开头后跟0或1个(小数点后面跟0到2位数字) */
    private static final String FORMAT = "^(0|[1-9]\\d*)(\\.\\d{0,%s})?$";
    /** 正则表达式：0-9.之外的字符 */
    private static final Pattern SOURCE_PATTERN = Pattern.compile("[^0-9.]");

    /** 默认保留小数点后2位 */
    private Pattern mPattern = Pattern.compile(String.format(FORMAT, "2"));

    /** 允许输入的最大金额 */
    private double maxValue = 999999;

    private String remindStr = "可输入最大数量";

    /**
     * 设置保留小数点后的位数，默认保留2位
     *
     * @param length
     */
    public void setDecimalLength(int length) {
        mPattern = Pattern.compile(String.format(FORMAT, length));
    }

    public static InterfaceObject getInterfaceUtils() {
        if (interfaceObject == null) {
            synchronized (NetApi.class) {
                if (interfaceObject == null) {
                    interfaceObject = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceObject.class);
                }
            }
        }
        return interfaceObject;
    }

    /**
     * 设置允许输入的最大金额
     *
     * @param maxValue
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 设置允许输入的最大金额
     *
     * @param maxValue
     */
    public void setMaxValue(String remindStr, double maxValue) {
        this.maxValue = maxValue;
        this.remindStr = remindStr;
    }

}
