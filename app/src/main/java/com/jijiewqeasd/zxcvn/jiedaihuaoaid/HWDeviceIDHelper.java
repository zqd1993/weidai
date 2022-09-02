package com.jijiewqeasd.zxcvn.jiedaihuaoaid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;

import java.util.concurrent.LinkedBlockingQueue;

public class HWDeviceIDHelper {
    private Context mContext;
    public final LinkedBlockingQueue<IBinder> linkedBlockingQueue = new LinkedBlockingQueue(1);

    public HWDeviceIDHelper(Context ctx) {
        mContext = ctx;
    }

    public void getHWID(DevicesIDsHelper.AppIdsUpdater _listener) {
        try {
            mContext.getPackageManager().getPackageInfo("com.huawei.hwid", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent bindIntent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        bindIntent.setPackage("com.huawei.hwid");

        boolean isBin = mContext.bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (isBin) {
            try {
                IBinder iBinder = linkedBlockingQueue.take();
                HWIDInterface.HWID hwID = new HWIDInterface.HWID(iBinder, mContext);
                String ids = hwID.getIDs();
                boolean boos = hwID.getBoos();
                String pps_oaid = hwID.getPPS_oaid();
                boolean support = isSupport();

                if (_listener != null) {
                    _listener.OnIdsAvalid(ids + "\npps_oadi: " + pps_oaid, support);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mContext.unbindService(serviceConnection);
            }
        }
    }


    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                linkedBlockingQueue.put(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public boolean isSupport() {
        try {
            PackageManager pm = mContext.getPackageManager();
            pm.getPackageInfo("com.huawei.hwid", 0);
            Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
            intent.setPackage("com.huawei.hwid");
            return !pm.queryIntentServices(intent, 0).isEmpty();

        } catch (Exception e) {
            return false;
        }
    }
}
