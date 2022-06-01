package com.dfgderv.erterqweq.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import butterknife.BindView;

import com.dfgderv.erterqweq.R;
import com.dfgderv.erterqweq.utils.StatusBarUtil;
import com.dfgderv.erterqweq.mvp.XActivity;

public class AboutMiaoJieActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    private NotificationManager manager;
    private RemoteViews mRemoteViews;
    private Notification mNotification;



    public void asd() {
//        manager =
//                (NotificationManager) RuliApp.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
    }

//    public static NotificationUtils getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
//
//    /**
//     * 创建实例
//     */
//    private static class SingletonHolder {
//        private static final NotificationUtils INSTANCE = new NotificationUtils();
//    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_about_miaojie;
    }

    @Override
    public Object newP() {
        return null;
    }
}
