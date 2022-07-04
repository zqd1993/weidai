package com.tysffgh.wfdgdfg.doudouadapter;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tysffgh.wfdgdfg.R;
import com.tysffgh.wfdgdfg.base.SimpleRecAdapter;
import com.tysffgh.wfdgdfg.kit.KnifeKit;
import com.tysffgh.wfdgdfg.doudoumodel.MineItemModelDouDou;
import com.tysffgh.wfdgdfg.doudouutils.SharedDouDouPreferencesUtilis;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;

public class MineDouDouAdapter extends SimpleRecAdapter<MineItemModelDouDou, MineDouDouAdapter.ViewHolder> {

    public MineDouDouAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_doudou_mine_item;
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private static String getCurrentProcessNameByApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private static String getCurrentProcessNameByActivityThread() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private static String getCurrentProcessNameByActivityManager(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        if (i == 3){
            viewHolder.arrowIcon.setVisibility(View.GONE);
            viewHolder.line.setVisibility(View.GONE);
            viewHolder.rightText.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("APP_MAIL"))) {
                viewHolder.rightText.setText(SharedDouDouPreferencesUtilis.getStringFromPref("APP_MAIL"));
            }
            viewHolder.parentLl.setOnClickListener(v -> {
                getRecItemClick().onItemClick(i, data.get(i), 2, viewHolder);
            });
        } else {
            viewHolder.rightText.setVisibility(View.GONE);
            viewHolder.arrowIcon.setVisibility(View.VISIBLE);
            viewHolder.line.setVisibility(View.VISIBLE);
            viewHolder.parentLl.setOnClickListener(v -> {
                getRecItemClick().onItemClick(i, data.get(i), 1, viewHolder);
            });
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private static String kjhgkuyik() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private static String erfghfgh() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private static String nhfguyujgd(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_title_tv)
        TextView tvItem;
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.parent_ll)
        View parentLl;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.arrow_icon)
        ImageView arrowIcon;
        @BindView(R.id.right_text)
        TextView rightText;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private static String ouigkjhg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private static String erfsfh() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private static String bfgsdrtfg(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

}
