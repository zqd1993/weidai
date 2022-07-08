package com.wolai.dai.jiekou;

import android.text.TextUtils;
import android.util.Log;

import com.wolai.dai.gongju.JiXinPreferencesOpenUtil;
import com.wolai.dai.net.XApi;

import java.io.FileReader;
import java.io.Reader;
import java.net.NetworkInterface;

public class JiXinApi {
    public static final String ZCXY = "/profile/ophmqb/zcxy.html";
    public static final String YSXY= "/profile/ophmqb/ysxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7746";

    private static JiXinInterfaceUtils interfaceUtils;

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = "";
        reader.close();
        return text;
    }

    public static JiXinInterfaceUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            synchronized (JiXinApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(JiXinPreferencesOpenUtil.getString("API_BASE_URL"), true).create(JiXinInterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    //得到有线网卡的MAC地址
    public static String getWireMac(){
        String strMacAddress = null;
        try {
            byte[] b = NetworkInterface.getByName("eth0")
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                System.out.println("b:"+(b[i]&0xFF));
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddress = buffer.toString().toUpperCase();
            Log.d("TAG",strMacAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strMacAddress;
    }

}
