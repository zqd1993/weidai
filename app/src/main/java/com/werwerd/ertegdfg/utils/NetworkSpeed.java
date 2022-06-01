package com.werwerd.ertegdfg.utils;

import android.net.TrafficStats;

public class NetworkSpeed {
    private static final String TAG = NetworkSpeed.class.getSimpleName();
    private double lastTotalRxBytes = 0;
    private double lastTimeStamp = 0;

    public String getNetSpeed(int uid) {
        double nowTotalRxBytes = getTotalRxBytes(uid);
        double nowTimeStamp = System.currentTimeMillis();
        double speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return String.valueOf(speed);
    }


    //getApplicationInfo().uid
    public long getTotalRxBytes(int uid) {
        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }
}