package com.wolai.dai.yemian;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wolai.dai.R;
import com.wolai.dai.gongju.JiXinStatusBarUtil;
import com.wolai.dai.kongjian.JixinRemindDialog;

import java.util.Iterator;
import java.util.List;

public class JixinZhuXiaoActivity extends AppCompatActivity {

    private JixinRemindDialog dialog;

    public static void callPhone(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JiXinStatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.jixin_activity_zhuxiao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new JixinRemindDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new JixinRemindDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    finish();
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    /**
     * 是否是在前台运行
     * @param context 上下文
     * @return 前台运行？
     */
    public static boolean isAppRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List list;
        if (Build.VERSION.SDK_INT > 20) {
            list = activityManager.getRunningAppProcesses();
            if (list == null) {
                return false;
            } else {
                String packageName = context.getPackageName();
                Iterator iterator = list.iterator();

                ActivityManager.RunningAppProcessInfo processInfo;
                do {
                    if (!iterator.hasNext()) {
                        return false;
                    }

                    processInfo = (ActivityManager.RunningAppProcessInfo)iterator.next();
                } while(processInfo.importance != 100 || !processInfo.processName.equals(packageName));

                return true;
            }
        } else {
            try {
                list = activityManager.getRunningTasks(1);
                if (list != null && list.size() >= 1) {
                    return context.getPackageName().equalsIgnoreCase(((ActivityManager.RunningTaskInfo)list.get(0)).baseActivity.getPackageName());
                } else {
                    return false;
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
